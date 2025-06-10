package com.pbl5.pbl5.controller;

import com.pbl5.pbl5.model.User;
import com.pbl5.pbl5.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewUserListController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/view-user-list")
    public String viewUserList(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
            return "redirect:/login";
        }
        model.addAttribute("userList", userRepository.findAll());
        return "viewUserList";
    }
}
