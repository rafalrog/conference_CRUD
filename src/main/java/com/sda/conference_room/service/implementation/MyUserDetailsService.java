package com.sda.conference_room.service.implementation;

import com.sda.conference_room.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .map(UserDetailsAdapter::new)
                .orElseThrow(()-> new UsernameNotFoundException("no such user"));
    }
}
