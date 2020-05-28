package com.lqkj.web.gnsc.modules.manager.service;

import com.google.common.collect.Lists;
import com.lqkj.web.gnsc.modules.log.service.GnsManageLogService;
import com.lqkj.web.gnsc.modules.manager.dao.GnsManageResourceRepository;
import com.lqkj.web.gnsc.modules.manager.dao.GnsManageResourceSQLDao;
import com.lqkj.web.gnsc.modules.manager.dao.GnsManageRoleRepository;
import com.lqkj.web.gnsc.modules.manager.dao.GnsManageUserRepository;
import com.lqkj.web.gnsc.modules.manager.domain.GnsManageResource;
import com.lqkj.web.gnsc.modules.manager.domain.GnsManageRole;
import com.lqkj.web.gnsc.modules.manager.domain.GnsManageRoleResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 用户权限服务
 */
@Service
@Transactional
public class GnsManageResourceService {

    @Autowired
    private GnsManageResourceSQLDao resourceSQLDao;

    private GnsManageResourceRepository resourceRepository;

    private GnsManageRoleRepository roleRepository;

    private GnsManageLogService gnsManageLogService;


    public GnsManageResourceService(GnsManageResourceRepository resourceRepository,
                                    GnsManageRoleRepository roleRepository,
                                    GnsManageLogService gnsManageLogService) {
        this.resourceRepository = resourceRepository;
        this.roleRepository = roleRepository;
        this.gnsManageLogService = gnsManageLogService;
    }

    public GnsManageResource add(GnsManageResource authority, String userName) {
        gnsManageLogService.addLog("用户权限服务", "add",
                "增加一个用户权限", userName);

        authority.setEnabled(Boolean.TRUE);
        GnsManageResource saveAuthority = resourceRepository.save(authority);

        GnsManageRole saveRule = roleRepository.getOne(1L);
        saveRule.getResources().add(saveAuthority);
        roleRepository.save(saveRule);
        return saveAuthority;
    }

    public void delete(Long[] id, String userName) {
        gnsManageLogService.addLog("用户权限服务", "delete",
                "删除一个用户权限", userName);

        for (Long i : id) {
            resourceRepository.deleteById(i);
        }
    }

    public GnsManageResource update(Long id, GnsManageResource authority, String userName) {
        gnsManageLogService.addLog("用户权限服务", "update",
                "更新一个用户权限", userName);

        GnsManageResource savedAuthority = resourceRepository.getOne(id);

        BeanUtils.copyProperties(authority, savedAuthority);

        HashSet<GnsManageResource> ccrUserAuthorities = new HashSet<>();

        queryChildAuth(ccrUserAuthorities, id);


        if (ccrUserAuthorities != null && ccrUserAuthorities.size() > 0) {
            Iterator<GnsManageResource> it = ccrUserAuthorities.iterator();
            while (it.hasNext()) {
                GnsManageResource auth = it.next();
                resourceRepository.updateChildState(auth.getAuthorityId(), savedAuthority.getEnabled());
            }
        }

        if (savedAuthority.getParentId() != null && savedAuthority.getEnabled()) {
            ccrUserAuthorities.clear();

            queryParentAuth(ccrUserAuthorities, savedAuthority.getParentId());

            if (ccrUserAuthorities != null && ccrUserAuthorities.size() > 0) {
                Iterator<GnsManageResource> it = ccrUserAuthorities.iterator();
                while (it.hasNext()) {
                    GnsManageResource auth = it.next();
                    resourceRepository.updateChildState(auth.getAuthorityId(), savedAuthority.getEnabled());
                }
            }
        }

        return resourceRepository.save(savedAuthority);
    }

    public void queryChildAuth(HashSet<GnsManageResource> ccrUserAuths, Long id) {
        List<GnsManageResource> ccrUserAuthorities = resourceRepository.queryChildAuth(id);
        if (ccrUserAuthorities != null && ccrUserAuthorities.size() > 0) {
            for (GnsManageResource gnsManageResource : ccrUserAuthorities) {
                ccrUserAuths.add(gnsManageResource);
                queryChildAuth(ccrUserAuths, gnsManageResource.getAuthorityId());
            }
        }
    }

    public void queryParentAuth(HashSet<GnsManageResource> ccrUserAuths, Long parentId) {
        if(parentId!=null){
            GnsManageResource parentAuth = resourceRepository.getOne(parentId);
            if (parentAuth != null) {
                ccrUserAuths.add(parentAuth);
                queryParentAuth(ccrUserAuths, parentAuth.getParentId());
            }
        }
    }

    public GnsManageResource info(Long id, String userName) {
        gnsManageLogService.addLog("用户权限服务", "info",
                "查询一个用户权限", userName);

        return resourceRepository.findById(id).get();
    }

    public Page<GnsManageResource> page(String name, String keyword, Integer page, Integer pageSize, String userName) {
        gnsManageLogService.addLog("用户权限服务", "page",
                "分页查询用户权限", userName);
        String k = keyword == null ? "" : keyword;

        return resourceRepository.findSupportAuthority(name, "%" + k + "%",
                PageRequest.of(page, pageSize));
    }

    public List<GnsManageResource> findByRuleId(Long ruleId, String userName) {
        gnsManageLogService.addLog("用户权限服务", "findByRuleId",
                "根据角色查询权限", userName);

        return Lists.newArrayList(roleRepository.getOne(ruleId).getResources());
    }

    public List<GnsManageResource> findByType(GnsManageResource.UserAuthorityType type, String userName) {
        gnsManageLogService.addLog("用户权限服务", "findByType",
                "根据类型查询权限", userName);
        if(type.name().equals("home_menu")){
            return resourceRepository.findByManageType(userName,type.name());
        }
        return resourceRepository.findByType(userName,type.name());
    }

    public void batchUpdateEnabled(Long[] authorities, Boolean enabled, String userName) {
        gnsManageLogService.addLog("用户权限服务", "batchUpdate",
                "批量更新权限状态", userName);

        for (Long authority : authorities) {
            GnsManageResource userAuthority = resourceRepository.getOne(authority);

            userAuthority.setEnabled(enabled);

            resourceRepository.save(userAuthority);
        }
    }

    public List<GnsManageResource> findByRoleAndUserId(String userId, String roles, String userName) {
        gnsManageLogService.addLog("用户权限服务", "findByRoleAndUserId",
                "查询更新权限状态列表", userName);

        String sql = "select * from ccr_user_authority " +
                " where 1 = 1";

        if (userId != null && roles != null) {
            sql += " and target_user_role && ARRAY[" + roles + ",'public'] \\:\\:varchar[] or specify_user_id && ARRAY['" + userId + "'] \\:\\:varchar[] group by authority_id";
        }

        return resourceSQLDao.executeSql(sql, GnsManageResource.class);
    }
}
