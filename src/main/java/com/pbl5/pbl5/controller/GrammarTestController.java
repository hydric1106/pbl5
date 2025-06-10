package com.pbl5.pbl5.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbl5.pbl5.model.Test;
import com.pbl5.pbl5.model.Question;
import com.pbl5.pbl5.model.Option;
import com.pbl5.pbl5.model.User;
import com.pbl5.pbl5.model.UserTestResult;
import com.pbl5.pbl5.model.UserProgress;
import com.pbl5.pbl5.repository.TestRepository;
import com.pbl5.pbl5.repository.QuestionRepository;
import com.pbl5.pbl5.repository.UserTestResultRepository;
import com.pbl5.pbl5.repository.UserProgressRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class GrammarTestController {

    @Autowired
    private TestRepository testRepo;
    @Autowired
    private QuestionRepository questionRepo;
    @Autowired
    private UserTestResultRepository userTestResultRepo;
    @Autowired
    private UserProgressRepository userProgressRepo;

    @GetMapping("/grammar-test")
    public String showGrammarTest(@RequestParam Long testId, Model model) {
        Test test = testRepo.findById(testId).orElse(null);
        model.addAttribute("test", test);
        return "grammarTest";
    }

    @PostMapping("/grammar-test/submit")
    public String submitGrammarTest(
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
        List<UserTestResultDTO> results = Arrays.asList(mapper.readValue(answers, UserTestResultDTO[].class));

        int totalQuestions = test.getQuestions().size();
        int scorePerQuestion = 100 / totalQuestions;
        int totalScore = 0;

        for (UserTestResultDTO r : results) {
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

            UserProgress progress = userProgressRepo.findByUserAndTest(user, test)
            .orElse(new UserProgress());
        progress.setUser(user);
        progress.setTest(test);
        progress.setScore(totalScore);
        progress.setCompletionStatus(UserProgress.CompletionStatus.completed);
        progress.setProgressPercentage(100);
        userProgressRepo.save(progress);

        model.addAttribute("score", totalScore);
        model.addAttribute("correctCount", results.stream().filter(UserTestResultDTO::isCorrect).count());
        model.addAttribute("incorrectCount", totalQuestions - (long)model.getAttribute("correctCount"));
        model.addAttribute("totalQuestions", totalQuestions);

        return "redirect:/testResult?testId=" + testId;
    }

    @PostMapping("/grammar-test/answer")
    @ResponseBody
    public String submitAnswer(
        @RequestBody UserTestResultDTO answer,
        HttpSession session
    ) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "NOT_LOGGED_IN";
        Question q = questionRepo.findById(answer.getQuestionId()).orElse(null);
        if (q == null) return "NO_QUESTION";
        Test test = q.getTest();

        List<UserTestResult> oldResults = userTestResultRepo.findAllByUserAndTestAndQuestion(user, test, q);
        UserTestResult utr;
        if (!oldResults.isEmpty()) {
            utr = oldResults.get(0);
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
        utr.setScoreObtained(answer.isCorrect() ? 100 : 0);
        utr.setSelectedAnswerText(answer.getSelectedOptionLabel());
        userTestResultRepo.save(utr);

        UserProgress progress = userProgressRepo.findByUserAndTest(user, test)
            .orElse(new UserProgress());
        progress.setUser(user);
        progress.setTest(test);
        progress.setScore(0);
        progress.setCompletionStatus(UserProgress.CompletionStatus.in_progress);
        progress.setProgressPercentage(0);
        userProgressRepo.save(progress);

        return "OK";
    }

    @GetMapping("/testResult")
    public String showTestResult(@RequestParam Long testId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            model.addAttribute("error", "Bạn cần đăng nhập để xem kết quả.");
            return "error";
        }
        Test test = testRepo.findById(testId).orElse(null);
        if (test == null) {
            model.addAttribute("error", "Test not found");
            return "error";
        }
        UserProgress progress = userProgressRepo.findByUserAndTest(user, test).orElse(null);

        int score = progress != null ? progress.getScore() : 0;
        int totalQuestions = test.getQuestions() != null ? test.getQuestions().size() : 0;

        List<UserTestResult> results = userTestResultRepo.findByUserAndTest(user, test);
        long correctCount = results.stream().filter(UserTestResult::getIsCorrect).count();
        long incorrectCount = totalQuestions - correctCount;

        model.addAttribute("score", score);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("correctCount", correctCount);
        model.addAttribute("incorrectCount", incorrectCount);

        return "testResult";
    }

    public static class UserTestResultDTO {
        private Long questionId;
        private String selectedOptionLabel;
        private boolean correct;

        public Long getQuestionId() { return questionId; }
        public void setQuestionId(Long questionId) { this.questionId = questionId; }
        public String getSelectedOptionLabel() { return selectedOptionLabel; }
        public void setSelectedOptionLabel(String selectedOptionLabel) { this.selectedOptionLabel = selectedOptionLabel; }
        public boolean isCorrect() { return correct; }
        public void setCorrect(boolean correct) { this.correct = correct; }
    }
}