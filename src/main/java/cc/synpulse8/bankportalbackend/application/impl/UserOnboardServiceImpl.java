package cc.synpulse8.bankportalbackend.application.impl;

import cc.synpulse8.bankportalbackend.application.UserOnboardService;
import cc.synpulse8.bankportalbackend.domain.model.client.res.EndUserInfoRes;
import cc.synpulse8.bankportalbackend.domain.model.entity.EndUserEntity;
import cc.synpulse8.bankportalbackend.domain.model.vo.LoginUser;
import cc.synpulse8.bankportalbackend.domain.service.UserServicesClient;
import cc.synpulse8.bankportalbackend.infrastructure.handler.GlobalServiceException;
import cc.synpulse8.bankportalbackend.presentation.dto.request.LoginRequest;
import cc.synpulse8.bankportalbackend.presentation.dto.response.LoginResponse;
import cc.synpulse8.bankportalbackend.util.jwt.JwtService;
import cc.synpulse8.bankportalbackend.util.rest.RestfulResponse;
import io.lettuce.core.RedisConnectionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.RedisConnectionFailureException;
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


    @Override
    public RestfulResponse<LoginResponse> login(LoginRequest loginUserRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserRequest.getSid(),
                        loginUserRequest.getPassword()
                )
        );

        //find user by sid with restTemplate
        EndUserInfoRes userInfoRes = userServicesClient.getUserInfoBySid(loginUserRequest.getSid());

        LoginUser user = new LoginUser();

        EndUserEntity userEntity = new EndUserEntity();

        userEntity.setUserName(userInfoRes.getUserName());
        userEntity.setSid(userInfoRes.getSid());


        user.setUser(userEntity);

        List<String> list = userServicesClient.getPermissionsBySid(loginUserRequest.getSid());

        user.setPermissions(list);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setSid(userInfoRes.getSid());
        loginResponse.setUserName(userInfoRes.getUserName());
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);

        log.info("存到 Redis 的 LoginUser" + user);
        log.info("Redis Host: " + redisHost);

        // redisTemplate.opsForHash.put("Login:" + user.getUser().getEmail(), user, expireTime, TimeUnit.MINUTES);

        try {
            redisTemplate.opsForHash().put("Login:" + user.getUser().getSid(), "user", user);
        } catch (RedisConnectionFailureException | RedisConnectionException e) {
            log.info("Redis Connection Failed");
            //shall throw 500 instead of 400
            throw new GlobalServiceException(GlobalServiceException.GlobalServiceErrorType.REDIS_CONNECTION_ERROR, "Redis Connection Failed");
        }

        return new RestfulResponse<LoginResponse>(loginResponse);
    }

}
