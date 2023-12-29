package com.sclab.boot.paymentwalletapp.controller;

import com.sclab.boot.paymentwalletapp.entity.User;
import com.sclab.boot.paymentwalletapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<User> create( @RequestBody User user) {
        return new ResponseEntity<User>(userService.create(user), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findById(@PathVariable Long userId) {
        var user = userService.findById(userId);
        if (user.getId() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> delete(@PathVariable Long userId) {
        return userService.deleteById(userId);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

}