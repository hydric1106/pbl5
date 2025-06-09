package com.pbl5.pbl5.controller;

import com.pbl5.pbl5.model.GrammarLesson;
import com.pbl5.pbl5.repository.GrammarLessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GrammarController {
    @Autowired
    private GrammarLessonRepository grammarLessonRepository;

    @GetMapping("/grammar-list")
    public String grammarList(Model model) {
        List<GrammarLesson> lessons = grammarLessonRepository.findAll();
        model.addAttribute("lessons", lessons);
        return "grammarList";
    }
}