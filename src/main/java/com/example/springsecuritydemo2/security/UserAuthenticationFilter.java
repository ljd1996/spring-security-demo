package com.example.springsecuritydemo2.security;

import com.example.springsecuritydemo2.domain.User;
import com.example.springsecuritydemo2.util.TokenDetailImpl;
import com.example.springsecuritydemo2.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * json web token 在请求头的名字
     */
    @Value("${token.header}")
    private String tokenHeader;

    /**
     * 辅助操作 token 的工具类
     */
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;

    public UserAuthenticationFilter() {
        this.setFilterProcessesUrl("/loginConfirm");
        this.setUsernameParameter("name");
        this.setPasswordParameter("password");
        this.setAuthenticationSuccessHandler(new LoginSuccessHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("attemptAuthentication...");
        User user = new User();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        if (name != null && password != null) {
            user.setName(name);
            user.setPassword(password);
        } else {
            return null;
        }
        System.out.println("user:" + user);
        return myAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication..." + authResult.getName());
        response.addHeader(this.tokenHeader, tokenUtils.generateToken(new TokenDetailImpl(authResult.getName(),
                ((MyUserDetails) authResult.getPrincipal()).getPassword())));
//        SecurityContextHolder.getContext().setAuthentication(authResult);
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
