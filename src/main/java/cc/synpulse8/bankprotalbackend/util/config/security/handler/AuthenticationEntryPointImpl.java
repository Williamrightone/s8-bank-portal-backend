package cc.synpulse8.bankprotalbackend.util.config.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        log.info("AuthenticationEntryPointImpl 接住 用戶帳號密碼不匹配: " + request.getServletPath() + " XX " + request.getRemoteAddr());

        throw new AuthenticationException("用戶帳號密碼不匹配") {
        };

    }
}
