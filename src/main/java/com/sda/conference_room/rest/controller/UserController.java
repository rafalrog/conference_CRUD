package com.sda.conference_room.rest.controller;
import com.sda.conference_room.exception.NotFoundException;
import com.sda.conference_room.model.dto.OrganizationDto;
import com.sda.conference_room.model.entity.User;
import com.sda.conference_room.service.implementation.UserService;
import com.sda.conference_room.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid final UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PutMapping("/org")
    public ResponseEntity<User> addUserToOrganization(@RequestBody OrganizationDto organizationDto, Principal principal) {
        if(principal!= null) {
            String user = principal.getName();
            User result = userService.setOrganization(user, organizationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        throw new NotFoundException("you are not logged");
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Principal principal){
        String userName = principal.getName();
        User user = userService.findUserByName(userName);
        return ResponseEntity.ok().body(user);
    }

}
