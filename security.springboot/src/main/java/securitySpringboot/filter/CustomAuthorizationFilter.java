package securitySpringboot.filter;

import static java.util.Arrays.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    // Alt + insert로 오버라이드 메소드 doFilterInternal 선택
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/api/login")) {   // 요청으로 온 서블렛 주소가 /api/login이면
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);   // 들어온 값 중에 리퀘스트 헤더를 받는다
            // Bearer는 헤더에 붙어서 토큰을 보내주는 장치
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {   // 헤더가 null이 아니고, 헤더가 Bearer로 시작한다면. ("Bearer " 공백을 한칸 붙여줘야 Bearer와 공백 후에 들어오는 토큰과 구분이 가능)
                try {   // 예외처리. try로 일단 시도
                    String token = authorizationHeader.substring("Bearer ".length());   // 토큰은 "Bearer " 뒷쪽부터 길이를 받아온다

                    // 이제 받아온 토큰을 풀어내서 해석할 것. 그래야 DB와 비교가 되므로
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());        // 토큰을 어떻게 풀어내나? 알고리즘을 통해서. 이 해석 알고리즘은 JWT의 기능
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);                      // 해석된 토큰이 이 변수에 담긴다

                    // userName
                    String userName = decodedJWT.getSubject();
                    // userRole
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();   // UserServiceImpl에 있는 유저가 생성되면 담는 객체. 빈 객체를 생성해서 여기에 담아줄 것
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));                // 반복문으로 컬렉션 배열에 하나씩 role이 들어간다
                    });
                    // userPassword ---
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception exception) {   // 안되면 여기로 가라
                    log.error("Error logging in : {}", exception.getMessage());   // 무슨 에러가 생겼는지 서버상에 로그 표시
                    response.setHeader("error", exception.getMessage());    // 무슨 에러가 생겼는지 응답 헤더에도 표시
                    response.setStatus(FORBIDDEN.value());                        // 포비든 http 상태 에러코드를 보냄
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", exception.getMessage());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);    // 이제 Json 타입으로 돌려주자
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }

            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
