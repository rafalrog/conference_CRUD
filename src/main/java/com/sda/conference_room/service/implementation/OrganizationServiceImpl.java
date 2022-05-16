package com.sda.conference_room.service.implementation;

import com.sda.conference_room.exception.NameIsNotUniqueException;
import com.sda.conference_room.exception.NotFoundException;
import com.sda.conference_room.mapper.OrganizationMapper;
import com.sda.conference_room.model.dto.OrganizationDto;
import com.sda.conference_room.model.entity.Organization;
import com.sda.conference_room.model.entity.User;
import com.sda.conference_room.repository.OrganizationRepository;
import com.sda.conference_room.repository.UserRepository;
import com.sda.conference_room.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {
        Optional<Organization> organizationFromDb = organizationRepository.findOrganizationByName(organizationDto.getName());
        if (organizationFromDb.isEmpty()) {
            Organization organizationToSave = OrganizationMapper.map(organizationDto);
            organizationRepository.save(organizationToSave);
            log.info("Organization with name {} saved", organizationToSave.getName());
            return OrganizationMapper.map(organizationToSave);
        }
        throw new NameIsNotUniqueException("Organization with that name already exists");
    }

    @Override
    public List<OrganizationDto> getALlOrganizations() {
        System.out.println(organizationRepository.findAll());
        return organizationRepository.findAll()
                .stream()
                .map(OrganizationMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public OrganizationDto getOrganizationById(Long organizationId) {
        Organization organization = getOrganizationFromDbById(organizationId);
        log.info("Organization with id {} was found", organizationId);
        return OrganizationMapper.map(organization);
    }

    @Override
    public OrganizationDto updateOrganization(Long organizationId, OrganizationDto organizationDto) {
        final Organization organizationFromRequest = OrganizationMapper.map(organizationDto);
        organizationFromRequest.setId(organizationId);
        organizationRepository.save(organizationFromRequest);
        log.info("Organization with id {} updated", organizationId);
        return OrganizationMapper.map(organizationFromRequest);
    }

    @Override
    public void deleteOrganizationById(Long organizationId) {
        organizationRepository.delete(getOrganizationFromDbById(organizationId));
        log.info("Organization with id {} deleted", organizationId);
    }

    @Override
    public Organization getOrganizationByName(String name) {
        return organizationRepository.findOrganizationByName(name).orElseThrow(() -> new NotFoundException("Organization with that name not found"));
    }

    @Override
    public List<User> getAllUsersOfOrganization(String organizationName) {
        return  userRepository.findAllByOrganization(getOrganizationByName(organizationName));
    }

    private Organization getOrganizationFromDbById(Long organizationId) {
        final Optional<Organization> organizationFromDatabase = organizationRepository.findById(organizationId);
        return organizationFromDatabase.orElseThrow(() -> new NotFoundException("Organization with given id not found"));
    }
}
