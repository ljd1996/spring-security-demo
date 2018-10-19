package com.example.springsecuritydemo2.security;

import com.example.springsecuritydemo2.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    @Value("${token.header}")
    private String tokenHeader;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("doFilter...");

        String authToken = request.getHeader(this.tokenHeader);
        if (authToken != null) {
            String username = this.tokenUtils.getUsernameFromToken(authToken);
            String password = this.tokenUtils.getPasswordFromToken(authToken);

            // 如果上面解析 token 成功并且拿到了 username 并且本次会话的权限还未被写入 且token有效
            if (username != null && password != null && SecurityContextHolder.getContext().getAuthentication()
                    == null && this.tokenUtils.validateToken(authToken)) {
                System.out.println("authToken != null");
                SecurityContextHolder.getContext().setAuthentication(myAuthenticationProvider.
                        authenticate(new UsernamePasswordAuthenticationToken(username, password)));
            }
        }
        chain.doFilter(request, response);
    }
}
