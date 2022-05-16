package com.sda.conference_room.config;

import com.sda.conference_room.model.entity.ConferenceRoom;
import com.sda.conference_room.model.entity.Organization;
import com.sda.conference_room.model.entity.Reservation;
import com.sda.conference_room.repository.ConferenceRoomRepository;
import com.sda.conference_room.repository.OrganizationRepository;
import com.sda.conference_room.repository.ReservationRepository;
import com.sda.conference_room.model.entity.User;
import com.sda.conference_room.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DbInit {

    private OrganizationRepository organizationRepository;
    private ConferenceRoomRepository conferenceRoomRepository;
    private ReservationRepository reservationRepository;
    private UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public DbInit(OrganizationRepository organizationRepository, ConferenceRoomRepository conferenceRoomRepository, ReservationRepository reservationRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.organizationRepository = organizationRepository;
        this.conferenceRoomRepository = conferenceRoomRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {

        User user = new User(1L,"admin", passwordEncoder.encode("admin"), "anime_role", null);
        userRepository.save(user);

        Organization org1 = Organization.builder().withName("org").build();
        organizationRepository.save(org1);

        Organization org2 = Organization.builder().withName("org22").build();
        organizationRepository.save(org2);

        ConferenceRoom cr1 = new ConferenceRoom();
        cr1.setName("cr");
        cr1.setOrganization(org1);
        conferenceRoomRepository.save(cr1);

        Reservation reservation = new Reservation();
        reservation.setConferenceRoom(cr1);
        reservationRepository.save(reservation);

    }
}

