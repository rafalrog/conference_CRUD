package com.sda.conference_room.config;

import com.sda.conference_room.model.entity.ConferenceRoom;
import com.sda.conference_room.model.entity.Organization;
import com.sda.conference_room.model.entity.Reservation;
import com.sda.conference_room.repository.ConferenceRoomRepository;
import com.sda.conference_room.repository.OrganizationRepository;
import com.sda.conference_room.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DbInit {

    private OrganizationRepository organizationRepository;
    private ConferenceRoomRepository conferenceRoomRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public DbInit(OrganizationRepository organizationRepository, ConferenceRoomRepository conferenceRoomRepository, ReservationRepository reservationRepository) {
        this.organizationRepository = organizationRepository;
        this.conferenceRoomRepository = conferenceRoomRepository;
        this.reservationRepository = reservationRepository;
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {

        Organization org1 = new Organization();
        org1.setName("org1");
        organizationRepository.save(org1);



        ConferenceRoom cr1 = new ConferenceRoom();
        cr1.setName("cr1");
        cr1.setOrganization(org1);
        conferenceRoomRepository.save(cr1);

        Reservation reservation = new Reservation();
        reservation.setConferenceRoom(cr1);
        reservationRepository.save(reservation);

//        Organization org2 = new Organization();
//        org2.setUserName("org2");
//        organizationRepository.save(org2);
//
//        ConferenceRoom cr2 = new ConferenceRoom();
//        cr2.setName("cr2");
//        cr2.setOrganization(org2);
//        conferenceRoomRepository.save(cr2);
//
//        Reservation reservation2 = new Reservation();
//        reservation2.setConferenceRoom(cr2);
//        reservationRepository.save(reservation2);

    }
}

