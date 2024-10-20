package com.ebaad.SpringSecurityProject.service;

import com.ebaad.SpringSecurityProject.model.MyUser;
import com.ebaad.SpringSecurityProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
// Custom UserDetailsService Class
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            var userObj = user.get();
            return  User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        }
        else {
            throw new UsernameNotFoundException("User does not exist!");
        }
    }

    // created this method as if there were many roles for the user
    private String[] getRoles(MyUser userObj) {
        if (userObj.getRole() == null){
            return new String[]{"USER"};
        }
        return userObj.getRole().split(",");
    }
}
