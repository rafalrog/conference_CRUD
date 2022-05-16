package com.sda.conference_room.mapper;

import com.sda.conference_room.model.dto.UserDto;
import com.sda.conference_room.model.entity.User;

public class UserMapper {

    public static UserDto map (User user){
        return UserDto.builder()
                .withId(user.getId())
                .withUsername(user.getUsername())
                .withPassword(user.getPassword())
                .withRole(user.getRole())
                .withOrganization(OrganizationMapper.map(user.getOrganization()))
                .build();
    }

    public static User map (UserDto userDto){
        return User.builder()
                .withId(userDto.getId())
                .withUsername(userDto.getUsername())
                .withPassword(userDto.getPassword())
                .withRole(userDto.getRole())
                .withOrganization(OrganizationMapper.map(userDto.getOrganization()))
                .build();
    }
}
