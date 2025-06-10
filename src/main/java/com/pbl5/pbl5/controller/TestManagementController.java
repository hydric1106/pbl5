package com.pbl5.pbl5.controller;

import com.pbl5.pbl5.model.Level;
import com.pbl5.pbl5.model.Test;
import com.pbl5.pbl5.model.Question;
import com.pbl5.pbl5.model.Option;
import com.pbl5.pbl5.repository.LevelRepository;
import com.pbl5.pbl5.repository.TestRepository;
import com.pbl5.pbl5.repository.QuestionRepository;
import com.pbl5.pbl5.repository.OptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;

@Controller
public class TestManagementController {

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private OptionRepository optionRepository;

    @GetMapping("/add-test")
    public String showAddTestForm(Model model) {
        model.addAttribute("test", null);
        model.addAttribute("levels", levelRepository.findAll());
        model.addAttribute("tests", testRepository.findAll()); // Thêm dòng này
        return "addTest";
    }

    @PostMapping("/add-test")
    public String createTest(@ModelAttribute Test test, @RequestParam(required = false) Integer levelId, Model model) {
        if (testRepository.existsByTitle(test.getTitle())) {
            model.addAttribute("levels", levelRepository.findAll());
            model.addAttribute("testNameExists", true);
            return "addTest";
        }
        if (levelId != null) {
            Level level = levelRepository.findById(levelId).orElse(null);
            test.setLevel(level);
        }
        testRepository.save(test);
        return "redirect:/add-test-question?testId=" + test.getId();
    }

    @GetMapping("/add-test-question")
    public String showAddQuestionForm(@RequestParam Long testId, Model model) {
        model.addAttribute("testId", testId);
        model.addAttribute("question", new Question());
        return "addTest";
    }

    @PostMapping("/add-test-question")
    public String addQuestion(
            @RequestParam Long testId,
            @ModelAttribute Question question,
            @RequestParam("optionLabel") List<String> optionLabels,
            @RequestParam("optionText") List<String> optionTexts,
            @RequestParam("correctOptionLabel") String correctOptionLabel
    ) {
        Test test = testRepository.findById(testId).orElse(null); // testId là Long
        if (test == null) return "redirect:/add-test";
        question.setTest(test);
        question.setCorrectOptionLabel(correctOptionLabel);
        questionRepository.save(question);

        for (int i = 0; i < optionLabels.size(); i++) {
            Option option = new Option();
            option.setQuestion(question);
            option.setOptionLabel(optionLabels.get(i));
            option.setOptionText(optionTexts.get(i));
            optionRepository.save(option);
        }
        return "redirect:/add-test-question?testId=" + testId;
    }
}