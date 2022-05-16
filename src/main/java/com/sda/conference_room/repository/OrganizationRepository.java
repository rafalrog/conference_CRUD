package com.sda.conference_room.repository;

import com.sda.conference_room.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findOrganizationByName(String name);


}
