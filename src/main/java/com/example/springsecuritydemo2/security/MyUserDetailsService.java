package com.example.springsecuritydemo2.security;

import com.example.springsecuritydemo2.dao.RoleMapper;
import com.example.springsecuritydemo2.dao.UserMapper;
import com.example.springsecuritydemo2.domain.Role;
import com.example.springsecuritydemo2.domain.RoleExample;
import com.example.springsecuritydemo2.domain.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.example.springsecuritydemo2.domain.User user;
        List<Role> roles;
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameEqualTo(s);
        List<com.example.springsecuritydemo2.domain.User> users = userMapper.selectByExample(userExample);

        if (users.size() <= 0) {
            return null;
        } else {
            user = users.get(0);

            RoleExample roleExample = new RoleExample();
            RoleExample.Criteria criteria1 = roleExample.createCriteria();
            criteria1.andUseridEqualTo(user.getId());
            roles = roleMapper.selectByExample(roleExample);
        }
        return new MyUserDetails(user, roles);
    }
}
