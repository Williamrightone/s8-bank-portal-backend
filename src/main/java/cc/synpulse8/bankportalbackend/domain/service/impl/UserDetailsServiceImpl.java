package cc.synpulse8.bankportalbackend.domain.service.impl;

import cc.synpulse8.bankportalbackend.domain.model.client.res.EndUserInfoRes;
import cc.synpulse8.bankportalbackend.domain.model.entity.EndUserEntity;
import cc.synpulse8.bankportalbackend.domain.model.vo.LoginUser;
import cc.synpulse8.bankportalbackend.domain.service.UserServicesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserServicesClient userServicesClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //username is SID, to fit the spring security api

        EndUserInfoRes userInfoRes = userServicesClient.getUserInfoBySid(username);

        EndUserEntity user = new EndUserEntity();

        user.setSid(userInfoRes.getSid());
        user.setUserName(userInfoRes.getUserName());
        user.setPassword(userInfoRes.getPasswd());

        LoginUser loginUser = new LoginUser();

        loginUser.setUser(user);

        List<String> permissions = userServicesClient.getPermissionsBySid(username);

        loginUser.setPermissions(permissions);

        return loginUser;
    }
}
