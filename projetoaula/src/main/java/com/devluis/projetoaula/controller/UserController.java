package com.devluis.projetoaula.controller;

import com.devluis.projetoaula.entity.User;
import com.devluis.projetoaula.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userServices;

    public UserController(UserService userServices) {
        this.userServices = userServices;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        var userId = userServices.createUser(createUserDto);

        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        var user = userServices.getUserById(userId);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        var users = userServices.listUsers();

        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId,
                                               @RequestBody UpdateUserDto updateUserDto) {
        userServices.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId) {
        userServices.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}