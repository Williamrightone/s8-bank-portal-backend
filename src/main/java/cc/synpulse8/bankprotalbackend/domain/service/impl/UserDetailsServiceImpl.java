package cc.synpulse8.bankprotalbackend.domain.service.impl;

import cc.synpulse8.bankprotalbackend.domain.model.client.res.EndUserInfoRes;
import cc.synpulse8.bankprotalbackend.domain.model.entity.EndUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserServicesClient userServicesClient;

    @Autowired
    private AuthenticationServiceClient authenticationServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Using restTemplate to get user info from user service
        EndUserInfoRes userInfoRes = userServicesClient.getEndUserByEmail(username);

        EndUserEntity user = new EndUserEntity();

        user.setEmail(userInfoRes.getEmail());
        user.setUserName(userInfoRes.getUserName());
        user.setPassword(userInfoRes.getPassword());

        LoginUser loginUser = new LoginUser();

        loginUser.setUser(user);

        List<String> list = authenticationServiceClient.getPermissionsByEmail(username);

        loginUser.setPermissions(list);

        return loginUser;
    }
}
