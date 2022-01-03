package securitySpringboot.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// 여기는 이해하는 것 보다는 그냥 따라 써보자
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // alt + insert로 오버라이드 메소드 만들어준다
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 인증서를 만드는 것
        String userName = request.getParameter("userName");   // api로 넘어오는 userName 데이터를 여기 담겠다는 뜻
        String password = request.getParameter("password");
        log.info("userName is : {}", userName);
        log.info("password id : {}", password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);   // 토큰 발행
        return authenticationManager.authenticate(authenticationToken);   // 내가 만든 토큰을 등록 시킨다
    }

    // alt + insert로 오버라이드 메소드 만들어준다
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // 여기에 유저가 로그인을 했을 때 토큰 발행하는 코드를 적어야 한다
        User user = (User) authentication.getPrincipal();   // 우리가 만든 모델 유저X. springframework.security.core.userdetails의 User
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());   // CustomAutorizationFilter에서 만든 알고리즘 이름 secret 그대로 사용
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))   // 첫 엑세스 토큰은 10분 동안 유지
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))   // 리프레시 시에는 토큰을 30분 동안 유지
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        // 헤더 값에 들어감
        response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);
        // 바디 값에 들어감
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
