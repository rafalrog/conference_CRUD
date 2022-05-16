package com.sda.conference_room.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class UserDto {
    private Long id;

    private String username;

    private String password;

    private String role;

    private OrganizationDto organization;
}
