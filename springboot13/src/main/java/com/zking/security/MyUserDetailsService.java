package com.zking.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.trim().isEmpty()) {
            throw new UsernameNotFoundException("用户名不能为空");
        }
        //查询数据库
        String name = username.equals("admin") ? "admin" : "test";
        String password = encoder.encode("123");
        String authorities = username.equals("admin") ? "admin,delete,insert" : "insert,ROLE_user";
//        Collection<GrantedAuthority> authorities = AuthorityUtils
//                .commaSeparatedStringToAuthorityList("admin,delete,insert");
        return new User(
                name, password, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
    }
}
