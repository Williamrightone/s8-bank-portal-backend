package cc.synpulse8.bankprotalbackend.application.impl;

import cc.synpulse8.bankprotalbackend.application.UserOnboardService;
import cc.synpulse8.bankprotalbackend.domain.model.client.res.EndUserInfoRes;
import cc.synpulse8.bankprotalbackend.domain.model.entity.EndUserEntity;
import cc.synpulse8.bankprotalbackend.domain.model.vo.LoginUser;
import cc.synpulse8.bankprotalbackend.presentation.dto.request.LoginRequest;
import cc.synpulse8.bankprotalbackend.presentation.dto.response.LoginResponse;
import cc.synpulse8.bankprotalbackend.util.jwt.JwtService;
import cc.synpulse8.bankprotalbackend.util.rest.RestfulResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserOnboardServiceImpl implements UserOnboardService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Value("${redis.cache.expire-time:5}")
    private int expireTime;

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserServicesClient userServicesClient;

    @Autowired
    private AuthenticationServiceClient authenticationServiceClient;

    @Override
    public RestfulResponse<LoginResponse> login(LoginRequest loginUserRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserRequest.getInternalId(),
                        loginUserRequest.getPassword()
                )
        );

        //find user by email with restTemplate
        EndUserInfoRes userInfoRes = userServicesClient.getEndUserByEmail(loginUserRequest.getEmail());

        LoginUser user = new LoginUser();

        EndUserEntity userEntity = new EndUserEntity();

        userEntity.setEmail(userInfoRes.getEmail());
        userEntity.setCountry(userInfoRes.getCountry());
        userEntity.setCity(userInfoRes.getCity());
        userEntity.setAddress(userInfoRes.getAddress());
        userEntity.setPhoneNumber(userInfoRes.getPhoneNumber());
        userEntity.setUserName(userInfoRes.getUserName());

        user.setUser(userEntity);

        List<String> list = authenticationServiceClient.getPermissionsByEmail(loginUserRequest.getEmail());

        user.setPermissions(list);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setEmail(userInfoRes.getEmail());
        loginResponse.setUserName(userInfoRes.getUserName());
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setPhone(userInfoRes.getPhoneNumber());
        loginResponse.setAddress(userInfoRes.getAddress());
        loginResponse.setCity(userInfoRes.getCity());
        loginResponse.setCountry(userInfoRes.getCountry());

        log.info("存到 Redis 的 LoginUser" + user);
        log.info("Redis Host: " + redisHost);

        //TODO Connection Refused
        // redisTemplate.opsForHash.put("Login:" + user.getUser().getEmail(), user, expireTime, TimeUnit.MINUTES);

        redisTemplate.opsForHash().put("Login:" + user.getUser().getEmail(), "user", user);

        return new RestfulResponse<LoginResponse>(loginResponse);
    }

}
