package com.lqkj.web.gnsc.auth;

import com.lqkj.web.gnsc.modules.manager.domain.GnsManageRole;
import com.lqkj.web.gnsc.modules.manager.domain.GnsManageUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRulesAccessTokenConverter extends DefaultAccessTokenConverter {
    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        Authentication userAuthentication = authentication.getUserAuthentication();

        if (userAuthentication!=null) {
            Object principal = userAuthentication.getPrincipal();

            if (principal instanceof GnsManageUser) {
                GnsManageUser user = (GnsManageUser) principal;

                Set<String> rules = user.getRules().stream()
                        .map(GnsManageRole::getContent)
                        .collect(Collectors.toSet());
                response.put("rules", rules);
            }
        }
        response.putAll(super.convertAccessToken(token, authentication));

        return response;
    }
}
