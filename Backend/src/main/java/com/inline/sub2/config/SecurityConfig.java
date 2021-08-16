package com.inline.sub2.config;

import com.inline.sub2.api.service.UserService;
import com.inline.sub2.auth.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//Spring Security를 활성화
@EnableWebSecurity
//Controller에서 특정 페이지에 특정 권한이 있는 유저만 접근을 허용할 경우 @PreAuthorize 어노테이션을 사용하는데
//해당 어노테이션에 대한 설정을 활성화시키는 어노테이션.
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    // Password 인코딩 방식에 BCrypt 암호화 방식 사용
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 하지않음
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), userService)) //HTTP 요청에 JWT 토큰 인증 필터를 거치도록 필터를 추가
                .authorizeRequests() //시큐리티 처리에 HttpServletRequest를 이용한다는 것을 의미
                //antMatchers()는 특정한 경로를 지정합니다.
//                .anyRequest().permitAll()

                .antMatchers(HttpMethod.GET,"/users").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.DELETE,"/on-board/user/{email}").hasRole("ADMIN")

                .antMatchers(
                        "/users/login",
                        "/users",
                        "/users/reset-password",
                        "/users/duplicate/{email}",
                        "/office",
                        "/office/duplicate/{officeName}",
                        "/office/depts",
                        "/office/jobs",
                        "/on-board/user/{email}"
                ).permitAll()



                .antMatchers("/users/change-password",
                            "/users/me",
                            "/users/profile",
                            "/users/{userId}",
                            "/commute/{commuteId}/in",
                            "/commute/{commuteId}/out",
                            "/chat/{officeId}",
                            "/todos",
                            "/todos/{todoId}"
                            ).hasAnyRole("USER","ADMIN")


                .antMatchers("/admin/**",
                            "/on-board/user",
                        "/on-board/{officeId}"
                        ).hasRole("ADMIN")
                .antMatchers(
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**" ,
                        /*Probably not needed*/ "/swagger.json").permitAll()


                .antMatchers("/").authenticated()       //인증이 필요한 URL과 필요하지 않은 URL에 대하여 설정
                .anyRequest().permitAll()
//                .anyRequest().hasAnyRole("USER", "ADMIN")      //permitAll()는 모든 사용자가 접근할 수 있다는 것을 의미


                .and().cors();
    }
}