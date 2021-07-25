package com.inline.sub2.auth;

import com.inline.sub2.api.service.UserService;
import com.inline.sub2.db.entity.UserEntity;
import com.inline.sub2.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    private UserService userService;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // request에 Header(jwtToken)를 획득한다.
        // Read the Authorization header, where the JWT Token should be
        String header = request.getHeader("Authorization");

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // If header is present, try grab user principal from database and perform authorization
            Authentication authentication = getAuthentication(request);
            // jwt 토큰으로 부터 획득한 인증 정보(authentication) 설정.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            ResponseBodyWriteUtil.sendError(request, response, ex);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Transactional(readOnly = true)
    public Authentication getAuthentication(HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        // 요청 헤더에 Authorization 키값에 jwt 토큰이 포함된 경우에만, 토큰 검증 및 인증 처리 로직 실행.
        if (token != null) {
            // parse the token and validate it (decode)
            JWTVerifier verifier = JwtUtil.getVerifier();
            JwtUtil.handleError(token);
            DecodedJWT decodedJWT = verifier.verify(token.replace("Bearer ", ""));
            String email = decodedJWT.getSubject();

            // Search in the DB if we find the user by token subject (username)
            // If so, then grab user details and create spring auth token using username, pass, authorities/roles
            if (email != null) {
                // jwt 토큰에 포함된 계정 정보(userId) 통해 실제 디비에 해당 정보의 계정이 있는지 조회.
                UserEntity userEntity = userService.getUserByEmail(email);
                if(userEntity != null) {
                    // 식별된 정상 유저인 경우, 요청 context 내에서 참조 가능한 인증 정보(jwtAuthentication) 생성.
                    UserAuthDetail userAuthDetails = new UserAuthDetail(userEntity);
                    UsernamePasswordAuthenticationToken jwtAuthentication = new UsernamePasswordAuthenticationToken(email,
                            null, userAuthDetails.getAuthorities());
                    jwtAuthentication.setDetails(userAuthDetails);
                    return jwtAuthentication;
                }
            }
            return null;
        }
        return null;
    }
}
