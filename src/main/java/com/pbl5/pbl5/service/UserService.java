package com.pbl5.pbl5.service;

import com.pbl5.pbl5.model.User;
import com.pbl5.pbl5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        user.setRole("USER");
        user.setActive(true);
        
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public boolean validateLogin(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
    
    public User updateProfile(User user) {
        return userRepository.save(user);
    }
    
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }
    
    public User updateUsername(Integer userId, String newUsername, String password) {
        User user = findById(userId);
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong pasword!");
        }
        
        if (userRepository.existsByUsername(newUsername)) {
            throw new RuntimeException("Username already exists!");
        }
        
        user.setUsername(newUsername);
        return userRepository.save(user);
    }
    
    public User updateEmail(Integer userId, String newEmail, String password) {
        User user = findById(userId);
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong password!");
        }
        
        if (userRepository.existsByEmail(newEmail)) {
            throw new RuntimeException("Email already exists!");
        }
        
        user.setEmail(newEmail);
        return userRepository.save(user);
    }
    
    public void changePassword(Integer userId, String currentPassword, String newPassword) {
        User user = findById(userId);
        
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("The current password is incorrect!");
        }
        
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new RuntimeException("The new password must not be the same as the old password!");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    
    public void deleteAccount(Integer userId, String password) {
        User user = findById(userId);
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong password!");
        }
        
        userRepository.delete(user);
    }
}