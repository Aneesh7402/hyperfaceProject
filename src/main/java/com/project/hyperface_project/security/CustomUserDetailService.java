package com.project.hyperface_project.security;

import com.project.hyperface_project.model.UserAuth;
import com.project.hyperface_project.repo.UserAuthRepo;
import com.project.hyperface_project.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserAuthRepo userAuthRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        try {
            UserAuth userAuth = userAuthRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

            return new User(userAuth.getUsername(), userAuth.getPassword(),
                    mapAuthToRoles(Stream.of(userAuth.getRole()).collect(Collectors.toList())));
        }catch (Exception e){

            return null;
        }
    }

    public List<SimpleGrantedAuthority> mapAuthToRoles(List<UserRole> roles){

        return roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList());
    }
}
