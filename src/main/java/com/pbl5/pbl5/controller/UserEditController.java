package com.pbl5.pbl5.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pbl5.pbl5.model.User;
import com.pbl5.pbl5.repository.UserRepository;

@RestController
public class UserEditController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/edit")
    public ResponseEntity<?> editUser(@RequestBody Map<String, String> payload) {
        Integer id = Integer.valueOf(payload.get("id")); 
        String username = payload.get("username");
        String email = payload.get("email");
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setUsername(username);
            user.setEmail(email);
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}