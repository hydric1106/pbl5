package com.pbl5.pbl5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pbl5.pbl5.repository.TestRepository;
import com.pbl5.pbl5.repository.QuestionRepository;
import com.pbl5.pbl5.repository.OptionRepository;
import com.pbl5.pbl5.model.Test;
import com.pbl5.pbl5.model.Question;
import com.pbl5.pbl5.model.Option;
import java.util.List;

@Service
public class ListeningTestService {
    
    @Autowired
    private TestRepository testRepository;
    
    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private OptionRepository optionRepository;

    @Transactional(readOnly = true)
    public List<Test> getAllListeningTests() {
        List<Test> tests = testRepository.findByTestType("Listening");
        System.out.println("Found " + tests.size() + " listening tests");
        
        for (Test test : tests) {
            List<Question> questions = getQuestionsForTest(test.getId());
            test.setQuestions(questions);
            System.out.println("Test " + test.getId() + " has " + questions.size() + " questions");
            
            for (Question question : questions) {
                List<Option> options = getOptionsForQuestion(question.getId());
                question.setOptions(options);
                System.out.println("Question " + question.getId() + " has " + options.size() + " options");
            }
        }
        
        return tests;
    }

    @Transactional(readOnly = true)
    public List<Question> getQuestionsForTest(Long testId) {
        return questionRepository.findByTestId(testId);
    }

    @Transactional(readOnly = true)
    public List<Option> getOptionsForQuestion(Long questionId) {
        List<Option> options = optionRepository.findByQuestionId(questionId);
        System.out.println("Fetched " + options.size() + " options for question " + questionId);
        return options;
    }
}