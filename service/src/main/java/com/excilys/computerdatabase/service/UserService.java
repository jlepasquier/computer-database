package com.excilys.computerdatabase.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.dao.UserDAO;
import com.excilys.computerdatabase.model.User;;;

@Service("userService")
public class UserService implements UserDetailsService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toSet());
        
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                grantedAuthorities);
    }

}
