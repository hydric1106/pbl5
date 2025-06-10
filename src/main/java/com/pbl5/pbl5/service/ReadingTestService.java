package com.pbl5.pbl5.service;

import com.pbl5.pbl5.model.Test;
import com.pbl5.pbl5.repository.TestRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReadingTestService {

    @Autowired
    private TestRepository testRepository;

    @Transactional(readOnly = true)
    public List<Test> getAllReadingTests() {
        List<Test> tests = testRepository.findByTestType("Reading");
        for (Test test : tests) {
            Hibernate.initialize(test.getQuestions());
            test.getQuestions().forEach(q -> Hibernate.initialize(q.getOptions()));
        }
        return tests;
    }
}
