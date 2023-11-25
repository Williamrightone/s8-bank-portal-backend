package cc.synpulse8.bankprotalbackend.util.filter;

import cc.synpulse8.bankprotalbackend.domain.model.vo.LoginUser;
import cc.synpulse8.bankprotalbackend.infrastructure.handler.GlobalServiceException;
import cc.synpulse8.bankprotalbackend.util.jwt.JwtService;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        //如果是 login 的请求，直接放行 因為沒有 token
        if (request.getServletPath().contains("/api/end-user/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        //如果沒有 Authorization header 或是不是 Bearer 開頭的，直接放行
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //去除 Bearer
        jwt = authHeader.substring(7);

        try {

            //解析 Token 取得 user email 若格式不隊則會拋出錯誤
            userEmail = jwtService.extractUsername(jwt);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                log.info("Checking Redis user: " + redisTemplate.opsForHash().get("Login:" + userEmail, "user"));
                Gson gson = new Gson();
                //T到 redis 查詢 token 是否存在
                UserDetails userDetails = (LoginUser) gson.fromJson(redisTemplate.opsForHash().get("Login:" + userEmail, "user").toString(), LoginUser.class);

                //如果 token 有效，就把 user 設定到 SecurityContext

                if (jwtService.isTokenValid(jwt, userDetails)) {

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }

            filterChain.doFilter(request, response);

        } catch (GlobalServiceException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }

    }
}
