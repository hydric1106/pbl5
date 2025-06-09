package com.pbl5.pbl5.controller;

import com.pbl5.pbl5.model.User;
import com.pbl5.pbl5.model.UserProgress;
import com.pbl5.pbl5.repository.UserProgressRepository;
import com.pbl5.pbl5.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProgressRepository userProgressRepo;

    @GetMapping("/login")
    public String showLoginForm(Model model, @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        try {
            if (userService.validateLogin(username, password)) {
                User user = userService.findByUsername(username);
                session.setAttribute("currentUser", user);
                return "redirect:/";
            } else {
                redirectAttributes.addFlashAttribute("error", "Username or password is incorrect! Please try again.");
                return "redirect:/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi đăng nhập: " + e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute User user,
                                     RedirectAttributes redirectAttributes) {
        try {
            userService.registerNewUser(user);
            redirectAttributes.addFlashAttribute("success", "Register successfully! Please login.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        List<UserProgress> progressList = userProgressRepo.findByUser(currentUser);

        model.addAttribute("user", currentUser);
        model.addAttribute("progressList", progressList);
        return "profile";
    }

    @GetMapping("/settings")
    public String showSettings(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }


        User user = userService.findById(currentUser.getId());
        model.addAttribute("user", user);
        return "settings";
    }


    @PostMapping("/settings/update-username")
    public String updateUsername(
            @RequestParam String newUsername,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            User updatedUser = userService.updateUsername(currentUser.getId(), newUsername, password);
            session.setAttribute("currentUser", updatedUser);
            redirectAttributes.addFlashAttribute("success", "Username updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/settings";
    }


    @PostMapping("/settings/update-email")
    public String updateEmail(
            @RequestParam String newEmail,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            User updatedUser = userService.updateEmail(currentUser.getId(), newEmail, password);
            session.setAttribute("currentUser", updatedUser);
            redirectAttributes.addFlashAttribute("success", "Email updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/settings";
    }


    @PostMapping("/settings/change-password")
    public String changePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu mới và mật khẩu xác nhận không khớp!");
            return "redirect:/settings";
        }

        try {
            userService.changePassword(currentUser.getId(), currentPassword, newPassword);
            redirectAttributes.addFlashAttribute("success", "Mật khẩu đã được thay đổi thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/settings";
    }


    @PostMapping("/settings/delete-account")
    public String deleteAccount(
            @RequestParam String password,
            @RequestParam String confirmText,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (!confirmText.equals("XÓA TÀI KHOẢN")) {
            redirectAttributes.addFlashAttribute("error", "Chuỗi xác nhận không chính xác!");
            return "redirect:/settings";
        }

        try {
            userService.deleteAccount(currentUser.getId(), password);
            session.invalidate();
            redirectAttributes.addFlashAttribute("success", "Tài khoản đã được xóa thành công!");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/settings";
        }
    }
}