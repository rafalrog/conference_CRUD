package com.sda.conference_room.service;

import com.sda.conference_room.model.dto.OrganizationDto;
import com.sda.conference_room.model.entity.Organization;
import com.sda.conference_room.model.entity.User;

import java.util.List;

public interface OrganizationService {

    OrganizationDto saveOrganization(OrganizationDto organizationDto);

    List<OrganizationDto> getALlOrganizations();

    OrganizationDto getOrganizationById(Long id);

    OrganizationDto updateOrganization(Long organizationId, OrganizationDto organizationDto);

    void deleteOrganizationById(Long id);

    Organization getOrganizationByName(String name);

    List<User> getAllUsersOfOrganization(String organizationName);
}
