package com.pbl5.pbl5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pbl5.pbl5.repository.VocabularyRepository;
import com.pbl5.pbl5.model.Vocabulary;
import java.util.List;

@Service
public class VocabularyService {
    
    @Autowired
    private VocabularyRepository vocabularyRepository;
    
    @Transactional(readOnly = true)
    public List<Vocabulary> findByCategoryId(Integer categoryId) {
        return vocabularyRepository.findByCategoryId(categoryId);
    }

    public List<Vocabulary> getVocabularyByCategoryId(Integer categoryId) {
        return vocabularyRepository.findByCategoryId(categoryId);
    }

}