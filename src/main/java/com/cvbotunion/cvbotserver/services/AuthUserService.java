package com.cvbotunion.cvbotserver.services;

import com.cvbotunion.cvbotserver.documents.UserDocument;
import com.cvbotunion.cvbotserver.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class AuthUserService implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDocument userDocument = this.userRepository.findByUsername(username);
        if(userDocument != null){
            return new User(userDocument.getUsername(), userDocument.getPassword(), new ArrayList<>());
        }else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
