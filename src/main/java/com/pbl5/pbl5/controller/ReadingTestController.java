package com.pbl5.pbl5.controller;

import com.pbl5.pbl5.model.Test;
import com.pbl5.pbl5.model.Question;
import com.pbl5.pbl5.model.Option;
import com.pbl5.pbl5.service.ReadingTestService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ReadingTestController {

    @Autowired
    private ReadingTestService readingTestService;

    @GetMapping("/reading-test")
    public String showReadingTestPage(Model model, HttpServletResponse response) {
        response.setHeader("Connection", "keep-alive");
        response.setHeader("Keep-Alive", "timeout=120");
        response.setHeader("X-Content-Type-Options", "nosniff");

        try {
            List<Test> readingTests = readingTestService.getAllReadingTests();

            for (Test test : readingTests) {
                List<Question> questions = test.getQuestions();
                for (Question question : questions) {
                    List<Option> options = question.getOptions();
                    System.out.println("Loaded " + options.size() + " options for question ID " + question.getId());
                }
            }

            model.addAttribute("tests", readingTests);
            return "readingTest";

        } catch (Exception e) {
            e.printStackTrace(); 
            return "error"; 
        }
    }
}
