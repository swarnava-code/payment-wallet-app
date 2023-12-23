package com.sclab.boot.paymentwalletapp.service;

import com.sclab.boot.paymentwalletapp.entity.User;
import com.sclab.boot.paymentwalletapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        var optUser = userRepository.findById(id);
        return optUser.orElseGet(User::new);
    }

    public ResponseEntity<User> update(User user) {
        if (findById(user.getId()).getId() == null) {
            return ResponseEntity.notFound().build();
        }
        var optUpdatedUser = userRepository.save(user);
        return ResponseEntity.ok(optUpdatedUser);
    }

    public ResponseEntity<User> deleteById(Long userId) {
        if (findById(userId).getId() == null) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}