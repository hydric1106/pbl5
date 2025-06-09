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

    // Giữ nguyên các phương thức hiện có
    public User registerNewUser(User user) {
        // Kiểm tra username và email đã tồn tại chưa
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email đã được sử dụng!");
        }

        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Mặc định là USER
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
    
    // Thêm các phương thức mới
    
    /**
     * Tìm người dùng theo ID
     */
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng!"));
    }
    
    /**
     * Cập nhật tên đăng nhập
     */
    public User updateUsername(Integer userId, String newUsername, String password) {
        User user = findById(userId);
        
        // Xác thực mật khẩu
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Mật khẩu không chính xác!");
        }
        
        // Kiểm tra tên đăng nhập mới có tồn tại chưa
        if (userRepository.existsByUsername(newUsername)) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }
        
        user.setUsername(newUsername);
        return userRepository.save(user);
    }
    
    /**
     * Cập nhật email
     */
    public User updateEmail(Integer userId, String newEmail, String password) {
        User user = findById(userId);
        
        // Xác thực mật khẩu
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Mật khẩu không chính xác!");
        }
        
        // Kiểm tra email mới có tồn tại chưa
        if (userRepository.existsByEmail(newEmail)) {
            throw new RuntimeException("Email đã được sử dụng!");
        }
        
        user.setEmail(newEmail);
        return userRepository.save(user);
    }
    
    /**
     * Đổi mật khẩu
     */
    public void changePassword(Integer userId, String currentPassword, String newPassword) {
        User user = findById(userId);
        
        // Xác thực mật khẩu hiện tại
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Mật khẩu hiện tại không chính xác!");
        }
        
        // Không cho phép đặt mật khẩu mới giống mật khẩu cũ
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new RuntimeException("Mật khẩu mới không được giống mật khẩu cũ!");
        }
        
        // Mã hóa và lưu mật khẩu mới
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    
    /**
     * Xóa tài khoản
     */
    public void deleteAccount(Integer userId, String password) {
        User user = findById(userId);
        
        // Xác thực mật khẩu
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Mật khẩu không chính xác!");
        }
        
        // Xóa người dùng
        userRepository.delete(user);
    }
}