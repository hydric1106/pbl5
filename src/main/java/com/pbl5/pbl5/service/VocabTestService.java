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
import org.hibernate.Hibernate;

@Service
public class VocabTestService {

    @Autowired
    private TestRepository testRepository;

    @Transactional(readOnly = true)
    public Test getTestByCategory(Integer categoryId) {
        Long categoryIdLong = categoryId.longValue();
        
        Test test = testRepository.findByCategoryIdAndType(categoryIdLong, "Vocabulary")
            .orElseThrow(() -> new RuntimeException("No vocabulary test found for category: " + categoryId));
            
        Hibernate.initialize(test.getQuestions());
        test.getQuestions().forEach(question -> {
            Hibernate.initialize(question.getOptions());
        });

        return test;
    }
}