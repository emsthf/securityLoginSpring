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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import securitySpringboot.filter.CustomAuthenticationFilter;
import securitySpringboot.filter.CustomAuthorizationFilter;

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
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");   // 기존의 스프링 시큐리티 로그인이 이 주소로 들어와

        http.csrf().disable();   // csrf는 사이트 간 요청 위조. disable로 막아준다는 뜻
        http.sessionManagement().sessionCreationPolicy(STATELESS);   // 세션 정책 정의
//        http.authorizeRequests().anyRequest().permitAll();   // 인증된 auth가 오면 처리 해줘라는 뜻. 지금은 뭐든지 받아주겠다는 뜻(애니 리퀘스트.permitAll)
        // 여기까지는 토큰을 발행하고 처리할 준비만 하는 것

        http.authorizeRequests().antMatchers("/api/login/**").permitAll();                                   // 로그인 뒤에 오는 모든 api를 허가 해주겠다는 뜻
        http.authorizeRequests().antMatchers("/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");   // user/sava api는 어드민만 쓰게 허락하겠다는 뜻
        http.authorizeRequests().antMatchers("/api/user/**").hasAnyAuthority("ROLE_USER");         // user로 들어오는 모든 권한은 유저가 쓰게 허락하겠다는 뜻
        http.authorizeRequests().anyRequest().authenticated();                                                          // 나머지는 로그인을 해서 쓰게한다
//        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));                                  // 우리가 만든 필터를 추가해 준다
        http.addFilter(customAuthenticationFilter);   // 위에 객체로 생성한 우리가 만든 필터를 추가
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);              // 위 필터를 하기 전에 체크 할 필터
    }

    // alt + insert를 눌러서 오버라이드 메소드를 만들어 준다. AuthenticationManagerBean() 메소드 선택
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
