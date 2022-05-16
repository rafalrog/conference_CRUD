package com.sda.conference_room.service.implementation;

import com.sda.conference_room.exception.NotFoundException;
import com.sda.conference_room.mapper.ReservationMapper;
import com.sda.conference_room.model.dto.ReservationDto;
import com.sda.conference_room.model.entity.Reservation;
import com.sda.conference_room.repository.ReservationRepository;
import com.sda.conference_room.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public List<ReservationDto> getAllReservations() {
        log.info("Getting all the reservations");
        final List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDto getReservationById(final Long id) {
        log.info("Getting a reservation by id: {}", id);
        return ReservationMapper.map(reservationRepository.getById(id));
    }

    @Override
    public ReservationDto createReservation(final ReservationDto reservationDto) {
        log.info("Creating reservation with id: {}", reservationDto.getId());
        final Reservation reservation = ReservationMapper.map(reservationDto);
        final Reservation createdReservation = reservationRepository.save(reservation);
        return ReservationMapper.map(createdReservation);
    }

    @Override
    public ReservationDto updateReservation(final ReservationDto reservationDto) {
        log.info("Updating reservation with id: {}", reservationDto.getId());
        getReservationFromDatabaseById(reservationDto.getId());
        final Reservation reservation = ReservationMapper.map(reservationDto);
        Reservation updatedReservation = reservationRepository.save(reservation);

        return ReservationMapper.map(updatedReservation);
    }

    @Override
    public void deleteReservationById(Long id) {
        log.info("Deleting reservation with id: {}", id);
        reservationRepository.delete(getReservationFromDatabaseById(id));
    }



    private Reservation getReservationFromDatabaseById(final Long id) {
        final Optional<Reservation> reservationFromDatabase = reservationRepository.findById(id);
        return reservationFromDatabase.orElseThrow(()-> new NotFoundException("Reservation with given id not found"));
    }
}
