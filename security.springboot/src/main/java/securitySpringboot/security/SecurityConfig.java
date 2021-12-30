package securitySpringboot.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import securitySpringboot.filter.CustomAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


// 이 클래스에 적는 것 대부분은 공식처럼 쓰이는 것
@Configuration   // ???
@EnableWebSecurity   // ???
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    // alt + insert를 눌러서 오버라이드 메소드를 만들어 준다. 열쇠 모양 configure 메소드 선택
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();   // csrf는 사이트 간 요청 위조. disable로 막아준다는 뜻
        http.sessionManagement().sessionCreationPolicy(STATELESS);   // 세션 정책 정의
        http.authorizeRequests().anyRequest().permitAll();   // 인증된 auth가 오면 처리 해줘라는 뜻
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));   // 우리가 만든 필터를 추가해 준다

        // 여기까지는 토큰을 발행하고 처리할 준비만 하는 것
    }

    // alt + insert를 눌러서 오버라이드 메소드를 만들어 준다. AuthenticationManagerBean() 메소드 선택
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
