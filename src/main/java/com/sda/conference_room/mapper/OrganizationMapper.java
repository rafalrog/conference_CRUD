package com.sda.conference_room.mapper;

import com.sda.conference_room.model.dto.OrganizationDto;
import com.sda.conference_room.model.entity.Organization;

public class OrganizationMapper {

    public static Organization map(final OrganizationDto dto) {
        return Organization.builder()
                .withId(dto.getId())
                .withName(dto.getName())
                .build();
    }

    public static OrganizationDto map(final Organization organization){
        return OrganizationDto.builder()
                .withId(organization.getId())
                .withName(organization.getName())
                .build();
    }
}
