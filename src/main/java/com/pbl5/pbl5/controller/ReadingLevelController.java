package com.pbl5.pbl5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.pbl5.pbl5.model.Level;
import com.pbl5.pbl5.repository.LevelRepository;
import java.util.List;

@Controller
public class ReadingLevelController {

    @Autowired
    private LevelRepository levelRepository;

    @GetMapping("/reading-test-choose-level")
    public String showChooseLevelPage(Model model) {
        List<Level> levels = levelRepository.findAll();
        model.addAttribute("levels", levels);
        return "readingTestChooseLevel";
    }
}
