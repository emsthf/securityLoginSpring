package securitySpringboot.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 여기에 유저가 로그인을 했을 때 토큰 발행하는 코드를 적어야 한다
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
