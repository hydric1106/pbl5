package com.pbl5.pbl5.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", length = 99, nullable = false)
    private String username;

    @Column(name = "password", length = 99, nullable = false)
    private String password;

    @Column(name = "email", length = 99, nullable = false)
    private String email;

    @Column(name = "image", length = 99, nullable = false)
    private String image = "";

    @Column(name = "role", length = 20, nullable = false)
    private String role = "USER";
    
    @Transient
    private boolean active = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserProgress> userProgress;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTestResult> testResults;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public List<UserProgress> getUserProgress() {
        return userProgress;
    }

    public void setUserProgress(List<UserProgress> userProgress) {
        this.userProgress = userProgress;
    }

    public List<UserTestResult> getTestResults() {
        return testResults;
    }

    public void setTestResults(List<UserTestResult> testResults) {
        this.testResults = testResults;
    }
}