package com.lqkj.web.gnsc.modules.manager.service;

import com.lqkj.web.gnsc.modules.log.service.GnsManageLogService;
import com.lqkj.web.gnsc.modules.manager.dao.GnsManageResourceRepository;
import com.lqkj.web.gnsc.modules.manager.dao.GnsManageRoleRepository;
import com.lqkj.web.gnsc.modules.manager.dao.GnsManageRoleResourceRepository;
import com.lqkj.web.gnsc.modules.manager.dao.GnsManageUserRepository;
import com.lqkj.web.gnsc.modules.manager.domain.GnsManageRole;
import com.lqkj.web.gnsc.modules.manager.domain.GnsManageUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * 用户角色服务
 */
@Service
@Transactional
public class GnsManageRoleService {

    @Autowired
    GnsManageUserRepository userRepository;

    @Autowired
    GnsManageRoleRepository roleRepository;

    @Autowired
    GnsManageResourceRepository resourceRepository;

    @Resource
    GnsManageRoleResourceRepository roleResourceRepository;

    @Autowired
    GnsManageLogService logService;

    public GnsManageRole add(String name, String enName, Long[] authorities, String userCode) {
        logService.addLog("用户角色服务", "add",
                "增加用户角色", userCode);

        //判断用户角色名是否存在
        Boolean exits=roleRepository.findByRuleName(name,enName).isEmpty();
        if(!exits){
            return null;
        }
        GnsManageRole rule = new GnsManageRole();

        rule.setName(name);
        rule.setContent(enName);
        rule.setResources(new HashSet<>());

        for (Long authority : authorities) {
            rule.getResources().add(resourceRepository.getOne(authority));
        }

        rule = roleRepository.save(rule);

        appendUserToNowUser(rule,userCode);

        return rule;
    }

    /**
     * 增加角色到当前用户
     */
    private void appendUserToNowUser(GnsManageRole rule, String userCode) {

        GnsManageUser user = userRepository.findByUserName(userCode);

        user.getRules().add(rule);

        userRepository.save(user);
    }

    @Transactional
    public Boolean delete(Long[] id) {
        logService.addLog("用户角色服务", "delete",
                "删除用户角色", null);

        for (Long i : id) {
            //查询该角色绑定的用户
            List<GnsManageUser> users = userRepository.ruleIdToUser(i);
            if(users.size()>1){
                return false;
            }
            //删除绑定的用户
            roleRepository.deleteUserRule(i);

            //先删除权限与角色的关联
            roleResourceRepository.deleteByRuleId(i);
            roleRepository.deleteById(i);
        }
        return true;
    }


    public List<GnsManageRole> updateAuthorities(String[] roles, Long[] authorities){
        logService.addLog("用户角色服务", "update",
                "更新用户角色对应权限", null);
        List<GnsManageRole> list = new ArrayList<>();
        if(roles != null && roles.length > 0){
            for (String role : roles) {

                GnsManageRole gnsManageRole = roleRepository.findByContent(role);

                Optional.ofNullable(gnsManageRole).ifPresent(v ->{
                    Long[] authorityArray = new Long[]{};
                    if (v.getResources() != null) {
                        List<Object> authorityList = new ArrayList<>(Arrays.asList(authorities));

                        v.getResources().forEach(s ->{
                            authorityList.add(s.getAuthorityId());
                        });

                        authorityArray = authorityList.stream().toArray(Long[] :: new);
                    }
                    update(v.getRuleId(),v.getName(),v.getContent(),authorityArray);
                    list.add(v);
                });
            }
        }

        return list;

    }

    public GnsManageRole update(Long id, String name, String enName, Long[] authorities) {
        logService.addLog("用户角色服务", "update",
                "更新用户角色", null);

        GnsManageRole rule = roleRepository.getOne(id);

        rule.setUpdateTime(new Timestamp(new Date().getTime()));

        if (rule.getResources()==null) {
            rule.setResources(new HashSet<>());
        } else {
            rule.getResources().clear();
        }

        for (Long authority : authorities) {
            rule.getResources().add(resourceRepository.getOne(authority));
        }

        return roleRepository.save(rule);
    }

    public GnsManageRole info(Long id) {
        logService.addLog("用户角色服务", "add",
                "查询用户角色", null);

        return roleRepository.findById(id).get();
    }

    public Page<GnsManageRole> page(String userName, String keyword, Integer page, Integer pageSize) {
        logService.addLog("用户角色服务", "add",
                "分页查询用户角色", null);

        String k = keyword==null ? "" : keyword;

        return roleRepository.findSupportRules(userName, "%" + k + "%",
                PageRequest.of(page, pageSize));
    }

    public List<GnsManageRole> ruleAll() {
        return roleRepository.findAll();
    }

}
