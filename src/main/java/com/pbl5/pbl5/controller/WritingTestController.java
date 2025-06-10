package com.pbl5.pbl5.controller;

import com.pbl5.pbl5.model.Test;
import com.pbl5.pbl5.service.WritingTestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WritingTestController {

    @Autowired
    private WritingTestService testService;

    @GetMapping("/writing-test")
    public String getWritingTests(@RequestParam("levelId") int levelId, Model model) {
        List<Test> tests = testService.getWritingTestsByLevel(levelId);
        model.addAttribute("tests", tests);
        return "writingTest";
    }
}
