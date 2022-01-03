# securityLoginSpring

Spring Security 간단 사용법
http.authorizeRequests().antMatchers("/api/user/**").hasAnyAuthority(("ROLE_ADMIN"));
을 통해서 ADMIN 권한이 있어야 /api/user/**의 권한을 사용가능

이를 실험해보기 위해서 
포스트맨으로 ADMIN으로 로그인해보자
Body에 x-www-form-urlcoded 형식으로
키값은 userName과 password를 넣어주고 벨류값은 해당 어드민의 userName과 password를 입력
(예 - key : userName/ value : Tim Cook, key : password/ value : abcd1234)
http://localhost:8080/api/login 로 api를 던져보면 어드민의 토큰을 받아올 수 있다
엑세스 토큰을 복사해서
http://localhost:8080/api/user/getAll 로 getAllUser api를 던질 때, 
헤더에 key : Authorization/ value : Bearer 어드민 토큰값 을 주면
어드민 권한을 가지고 있기 때문에 모든 유저의 정보를 불러오는 것을 볼 수 있다

이번엔 포스트맨으로 USER의 권한만 가지고 있는 계정으로 로그인을 해보자
키값은 userName과 password를 넣어주고 벨류값은 해당 USER의 userName과 password를 입력
(예 - key : userName/ value : Khalid, key : password/ value : qwer789)
http://localhost:8080/api/login 로 api를 던져보면 USER의 토큰을 받아올 수 있다
엑세스 토큰을 복사해서
http://localhost:8080/api/user/getAll 로 getAllUser api를 던질 때, 
헤더에 key : Authorization/ value : Bearer 유저 토큰값 을 주면
/api/user/**의 권한은 어드민이어야 사용할 수 있는 api 권한이므로 일반 USER 계정은 403 포비든 오류가 뜨면서
권한이 필요하다고 하는 것을 볼 수 있다


Step5가 Spring Security 권한 로그인 완성본
