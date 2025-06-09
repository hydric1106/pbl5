package com.pbl5.pbl5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pbl5.pbl5.service.CategoryService;
import com.pbl5.pbl5.service.VocabularyService;
import com.pbl5.pbl5.model.Category;
import java.util.List;

@Controller
public class VocabularyController {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private VocabularyService vocabularyService;

    @GetMapping("/vocab-list")
    public String showVocabList(@RequestParam(required = false) Integer categoryId, Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        
        if (categoryId != null) {
            model.addAttribute("vocabularies", vocabularyService.findByCategoryId(categoryId));
        } else if (!categories.isEmpty()) {
            model.addAttribute("vocabularies", 
                vocabularyService.findByCategoryId(categories.get(0).getId()));
            categoryId = categories.get(0).getId();
        }
        
        model.addAttribute("selectedCategoryId", categoryId);
        return "vocabList";
    }
}