package com.pbl5.pbl5.controller;

import com.pbl5.pbl5.model.Test;
import com.pbl5.pbl5.model.Question;
import com.pbl5.pbl5.model.Option;
import com.pbl5.pbl5.service.ListeningTestService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ListeningTestController {

    @Autowired
    private ListeningTestService listeningTestService;

    @GetMapping("/listening-test")
    public String showListeningTestPage(Model model, HttpServletResponse response) {
        // Set HTTP headers to prevent caching and ensure compatibility
        response.setHeader("Connection", "keep-alive");
        response.setHeader("Keep-Alive", "timeout=120");
        response.setHeader("X-Content-Type-Options", "nosniff");

        try {
            // Lấy toàn bộ bài test dạng Listening
            List<Test> listeningTests = listeningTestService.getAllListeningTests();

            // Buộc load các câu hỏi và các lựa chọn tương ứng
            for (Test test : listeningTests) {
                List<Question> questions = test.getQuestions();
                for (Question question : questions) {
                    List<Option> options = question.getOptions();
                    // Log ra để kiểm tra xem có load đúng không
                    System.out.println("Loaded " + options.size() + " options for question ID " + question.getId());
                }
            }

            // Truyền sang view
            model.addAttribute("tests", listeningTests);
            return "listeningTest";

        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi để dễ debug
            return "error"; // Trả về trang lỗi nếu có exception
        }
    }
}
