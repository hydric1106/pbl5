package com.pbl5.pbl5.service;

import com.pbl5.pbl5.model.Test;
import com.pbl5.pbl5.repository.TestRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WritingTestService {

    @Autowired
    private TestRepository testRepository;

    public List<Test> getWritingTestsByLevel(int levelId) {
        List<Test> tests = testRepository.findByTestTypeAndLevel_Id("Writing", levelId);
        for (Test test : tests) {
            Hibernate.initialize(test.getQuestions());
            test.getQuestions().forEach(q -> Hibernate.initialize(q.getOptions()));
        }
        return tests;
    }
}