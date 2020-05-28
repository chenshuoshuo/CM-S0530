package com.lqkj.web.gnsc.modules.manager.service;

import com.lqkj.web.gnsc.modules.log.service.GnsManageLogService;
import com.lqkj.web.gnsc.modules.manager.dao.GnsManageUserRepository;
import com.lqkj.web.gnsc.modules.manager.domain.GnsManageUser;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * cas ticket处理服务
 */
@Service
@Transactional
public class GnsCasService {

    @Autowired
    GnsManageUserRepository userRepository;

    @Autowired
    GnsManageLogService logService;

    @Autowired
    PasswordEncoder passwordEncoder;


    /**
     * 更新用户ticket
     */
    public GnsManageUser updateTicket(String username, String ticket) throws DocumentException {
        logService.addLog("cas ticket处理服务", "updateTicket",
                "更新用户ticket", null);

        GnsManageUser user = userRepository.findByUserName(username);

        user.setCasTicket(passwordEncoder.encode(ticket));

        user.setPassWord(null);

        return userRepository.save(user);
    }
}
