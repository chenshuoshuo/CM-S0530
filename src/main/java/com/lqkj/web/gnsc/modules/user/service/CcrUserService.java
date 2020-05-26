package com.lqkj.web.gnsc.modules.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lqkj.web.gnsc.modules.user.dao.CcrUserRepository;
import com.lqkj.web.gnsc.modules.user.domain.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 用户管理服务
 */
@Service
@Transactional
public class CcrUserService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String UPLOAD_FILE_PATH = "./upload/user/";

    @Autowired
    CcrUserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${admin.code}")
    Integer adminCode;

    /**
     * 密码登录
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return userRepository.findByUserName(username);

}

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    /**
     * 注册管理员用户
     */
    public User registerAdmin(Integer adminCode, User ccrUser, String userName) throws Exception {
        if (!adminCode.equals(this.adminCode)) {
            throw new Exception("授权码不正确");
        }
        if(userRepository.findByUserName(ccrUser.getUsername())!=null){
            return null;
        }
        ccrUser.setAdmin(Boolean.TRUE);
        ccrUser.setPassWord(passwordEncoder.encode(ccrUser.getPassWord()));
        ccrUser.setUserAuthority(User.UserAuthority.superAdmin);
        ccrUser.setUserGroup(User.CcrUserGroupType.teacher_staff);
        //先根据用户名进行查询，看是否已经存在该用户

        return userRepository.save(ccrUser);
    }



}
