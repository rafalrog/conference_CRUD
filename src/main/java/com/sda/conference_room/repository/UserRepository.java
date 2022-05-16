package com.sda.conference_room.repository;

import com.sda.conference_room.model.entity.Organization;
import com.sda.conference_room.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String name);
    List<User> findAllByOrganization(Organization organization);
}
