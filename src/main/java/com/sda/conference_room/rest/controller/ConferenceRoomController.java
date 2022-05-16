package com.sda.conference_room.rest.controller;

import com.sda.conference_room.model.dto.ConferenceRoomDto;
import com.sda.conference_room.service.implementation.ConferenceRoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/conferenceroom")
@RequiredArgsConstructor
public class ConferenceRoomController {

    private final ConferenceRoomServiceImpl conferenceRoomService;

    @GetMapping("/all")
    public ResponseEntity<List<ConferenceRoomDto>> getAllConferenceRooms() {
        List<ConferenceRoomDto> conferenceRooms = conferenceRoomService.getAllConferenceRooms();
        return ResponseEntity.ok(conferenceRooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConferenceRoomDto> getConferenceRoomById(@PathVariable("id") final Long id) {
        ConferenceRoomDto conferenceRoom = conferenceRoomService.getConferenceRoomById(id);

        return ResponseEntity.ok(conferenceRoom);
    }

    @PostMapping("/add")
    public ResponseEntity<ConferenceRoomDto> addConferenceRoom(@RequestBody @Valid final ConferenceRoomDto conferenceRoom) {
        ConferenceRoomDto newConferenceRoom = conferenceRoomService.addConferenceRoom(conferenceRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(newConferenceRoom);
    }

    @PutMapping("/update")
    public ResponseEntity<ConferenceRoomDto> updateConferenceRoom(@RequestBody @Valid final ConferenceRoomDto conferenceRoom) {
        ConferenceRoomDto updatedConferenceRoom = conferenceRoomService.updateConferenceRoom(conferenceRoom);
        return ResponseEntity.status(HttpStatus.OK).body(updatedConferenceRoom);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteConferenceRoom(@PathVariable("id") final Long id) {
        conferenceRoomService.deleteConferenceRoomById(id);
        return ResponseEntity.noContent().build();
    }
}
