package com.pbl5.pbl5.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbl5.pbl5.model.Test;
import com.pbl5.pbl5.model.Question;
import com.pbl5.pbl5.model.Option;
import com.pbl5.pbl5.service.VocabTestService;
import com.pbl5.pbl5.dto.VocabTestResultDTO;
import com.pbl5.pbl5.repository.UserRepository;
import com.pbl5.pbl5.repository.TestRepository;
import com.pbl5.pbl5.repository.QuestionRepository;
import com.pbl5.pbl5.repository.UserTestResultRepository;
import com.pbl5.pbl5.repository.UserProgressRepository;
import com.pbl5.pbl5.model.User;
import com.pbl5.pbl5.model.UserTestResult;
import com.pbl5.pbl5.model.UserProgress;

import java.util.List;
import java.util.Arrays;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class VocabTestController {
    @Autowired
    private VocabTestService vocabTestService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TestRepository testRepo;
    @Autowired
    private QuestionRepository questionRepo;
    @Autowired
    private UserTestResultRepository userTestResultRepo;
    @Autowired
    private UserProgressRepository userProgressRepo;

    @GetMapping("/vocab-test")
    public String showVocabTest(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            Model model,
            HttpServletResponse response) {
        
        response.setHeader("Connection", "keep-alive");
        response.setHeader("Keep-Alive", "timeout=120");
        response.setHeader("X-Content-Type-Options", "nosniff");

        try {
            if (categoryId == null) {
                model.addAttribute("error", "Category ID is required");
                return "error";
            }

            Test test = vocabTestService.getTestByCategory(categoryId);
            
            // Verify data is loaded
            if (test != null && test.getQuestions() != null) {
                System.out.println("Test loaded with " + test.getQuestions().size() + " questions");
                test.getQuestions().forEach(q -> {
                    if (q.getOptions() != null) {
                        System.out.println("Question " + q.getId() + " has " + q.getOptions().size() + " options");
                    }
                });
            }

            model.addAttribute("test", test);
            return "vocabTest";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error loading test: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/vocab-test/submit")
    public String submitVocabTest(
        @RequestParam Long testId,
        @RequestParam String answers,
        HttpSession session,
        Model model
    ) throws JsonProcessingException {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            model.addAttribute("error", "Bạn cần đăng nhập để làm bài test.");
            return "error";
        }
        Test test = testRepo.findById(testId).orElseThrow();
        ObjectMapper mapper = new ObjectMapper();
        List<VocabTestResultDTO> results = Arrays.asList(mapper.readValue(answers, VocabTestResultDTO[].class));

        int totalQuestions = test.getQuestions().size();
        int scorePerQuestion = 100 / totalQuestions;
        int totalScore = 0;

        for (VocabTestResultDTO r : results) {
            Question q = questionRepo.findById(r.getQuestionId()).orElseThrow();
            boolean isCorrect = r.isCorrect();
            int score = isCorrect ? scorePerQuestion : 0;
            if (isCorrect) totalScore += score;

            UserTestResult utr = new UserTestResult();
            utr.setUser(user);
            utr.setTest(test);
            utr.setQuestion(q);
            utr.setSelectedOptionLabel(r.getSelectedOptionLabel());
            utr.setIsCorrect(isCorrect);
            utr.setScoreObtained(score);
            utr.setSelectedAnswerText(r.getSelectedOptionLabel()); 
            userTestResultRepo.save(utr);
        }

        // Lưu vào user_progress
        UserProgress progress = userProgressRepo.findByUserAndTest(user, test)
            .orElse(new UserProgress());
        progress.setUser(user);
        progress.setTest(test);
        progress.setScore(totalScore);
        progress.setCompletionStatus(UserProgress.CompletionStatus.completed);
        progress.setProgressPercentage(100);
        if (test.getCategories() != null && !test.getCategories().isEmpty()) {
            progress.setCategory(test.getCategories().get(0));
        }
        userProgressRepo.save(progress);

        model.addAttribute("score", totalScore);
        model.addAttribute("correctCount", results.stream().filter(VocabTestResultDTO::isCorrect).count());
        model.addAttribute("incorrectCount", totalQuestions - (long)model.getAttribute("correctCount"));
        model.addAttribute("totalQuestions", totalQuestions);

        return "testResult"; 
    }

    @PostMapping("/vocab-test/answer")
    @ResponseBody
    public String submitAnswer(
        @RequestBody VocabTestResultDTO answer,
        HttpSession session
    ) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "NOT_LOGGED_IN";
        Question q = questionRepo.findById(answer.getQuestionId()).orElse(null);
        if (q == null) return "NO_QUESTION";
        Test test = q.getTest();

        // Lấy tất cả kết quả cũ cho user, test, question
        List<UserTestResult> oldResults = userTestResultRepo.findAllByUserAndTestAndQuestion(user, test, q);
        UserTestResult utr;
        if (!oldResults.isEmpty()) {
            utr = oldResults.get(0); // lấy bản ghi đầu tiên
            // Nếu muốn, có thể xóa các bản ghi thừa:
            for (int i = 1; i < oldResults.size(); i++) {
                userTestResultRepo.delete(oldResults.get(i));
            }
        } else {
            utr = new UserTestResult();
        }
        utr.setUser(user);
        utr.setTest(test);
        utr.setQuestion(q);
        utr.setSelectedOptionLabel(answer.getSelectedOptionLabel());
        utr.setIsCorrect(answer.isCorrect());
        utr.setScoreObtained(answer.isCorrect() ? 100 : 0); // hoặc tính điểm phù hợp
        utr.setSelectedAnswerText(answer.getSelectedOptionLabel());
        userTestResultRepo.save(utr);

        // Lưu user_progress (cập nhật tiến độ từng câu)
        UserProgress progress = userProgressRepo.findByUserAndTest(user, test)
            .orElse(new UserProgress());
        progress.setUser(user);
        progress.setTest(test);
        progress.setScore(0); // hoặc tính tổng điểm nếu muốn
        progress.setCompletionStatus(UserProgress.CompletionStatus.in_progress);
        progress.setProgressPercentage(0); // hoặc tính %
        if (test.getCategories() != null && !test.getCategories().isEmpty()) {
            progress.setCategory(test.getCategories().get(0));
        }
        progress.setQuestion(q);
        userProgressRepo.save(progress);

        return "OK";
    }
}