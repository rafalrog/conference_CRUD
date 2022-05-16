package com.sda.conference_room.service.implementation;

import com.sda.conference_room.exception.NotFoundException;
import com.sda.conference_room.mapper.OrganizationMapper;
import com.sda.conference_room.model.dto.OrganizationDto;
import com.sda.conference_room.model.entity.Organization;
import com.sda.conference_room.model.entity.User;
import com.sda.conference_room.repository.OrganizationRepository;
import com.sda.conference_room.repository.UserRepository;
import com.sda.conference_room.service.OrganizationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class UserServiceIntegrationTest {

    @Autowired
    private  UserService userService;

    @Autowired
    private  OrganizationService organizationService;

    public static User tuser1, tuser2, tuser3;
    public static Organization torg1;
    public static OrganizationDto torgDto1;

    @BeforeAll
    public static void setUp(@Autowired UserRepository userRepository,@Autowired OrganizationRepository organizationRepository) {
        tuser1 = User.builder()
                .withUsername("user1")
                .build();
        tuser2 = User.builder()
                .withUsername("user2")
                .build();
        tuser3 = User.builder()
                .withUsername("user3")
                .build();
        userRepository.saveAll(List.of(tuser1, tuser2, tuser3));

        torg1 = Organization.builder()
                .withName("torg1")
                .build();
        torgDto1 = OrganizationMapper.map(torg1);

        organizationRepository.save(torg1);
    }

    @Test
    void setOrganizationShouldSetOrganization() {
        userService.setOrganization("user1", torgDto1);
        userService.setOrganization("user2", torgDto1);
        userService.setOrganization("user3", torgDto1);

        List<User> users = organizationService.getAllUsersOfOrganization("torg1");
        users.forEach(user -> System.out.println(user.getOrganization().getName()));


        assertThat(users).hasSize(3);
        assertThat(userService.findUserByName("user2").getOrganization().getName()).isEqualTo("torg1");
    }

    @Test
    void setOrganizationShouldThrowNotFoundException() {
        OrganizationDto fakeOrganization = OrganizationDto.builder().withName("doesntExist").build();

        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userService.setOrganization("user1", fakeOrganization));
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userService.setOrganization("user4", torgDto1));
    }
}