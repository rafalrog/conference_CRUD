package com.sda.conference_room.service.implementation;

import com.sda.conference_room.exception.NotFoundException;
import com.sda.conference_room.model.dto.OrganizationDto;
import com.sda.conference_room.model.entity.Organization;
import com.sda.conference_room.model.dto.UserDto;
import com.sda.conference_room.model.entity.User;
import com.sda.conference_room.repository.UserRepository;
import com.sda.conference_room.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OrganizationService organizationService;

    public User createUser(final UserDto userDto) {
        final User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole("ROLE_USER");
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User setOrganization(String userName, OrganizationDto organizationDto){                                      //++
        Organization organization = organizationService.getOrganizationByName(organizationDto.getName());
        User user = findUserByName(userName);
        user.setOrganization(organization);
        userRepository.save(user);
        return user;
    }

    public User findUserByName(String name) {                                                                           //++
        return userRepository.findUserByUsername(name).orElseThrow(() -> new NotFoundException("User with such name not found"));
    }
}
