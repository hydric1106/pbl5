package com.pbl5.pbl5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pbl5.pbl5.model.Category;
import com.pbl5.pbl5.model.Flashcard;
import com.pbl5.pbl5.model.FlashcardsCategory;
import com.pbl5.pbl5.model.Vocabulary;
import com.pbl5.pbl5.service.VocabularyService;
import com.pbl5.pbl5.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import com.pbl5.pbl5.model.User;
import com.pbl5.pbl5.repository.FlashcardsCategoryRepository;
import com.pbl5.pbl5.repository.FlashcardRepository;

@Controller
public class FlashcardController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private VocabularyService vocabularyService;

    @Autowired
    private FlashcardsCategoryRepository flashcardsCategoryRepository;

    @Autowired
    private FlashcardRepository flashcardRepository;

    @GetMapping("/flashcard-choose")
    public String showFlashcardChoosePage(HttpServletResponse response) {
        response.setHeader("Connection", "keep-alive");
        response.setHeader("Keep-Alive", "timeout=120");
        response.setHeader("X-Content-Type-Options", "nosniff");
        return "flashcardChoose";
    }

    @GetMapping("/flashcard-create")
    public String showFlashcardCreatePage(HttpServletResponse response) {
        response.setHeader("Connection", "keep-alive");
        response.setHeader("Keep-Alive", "timeout=120");
        response.setHeader("X-Content-Type-Options", "nosniff");
        return "flashcardCreate";
    }

    @GetMapping("/flashcard")
    public String showFlashcards(
        @RequestParam(required = false) Integer categoryId,
        @RequestParam(required = false) Integer flccategoryId,
        Model model,
        HttpServletResponse response,
        HttpSession session
    ) {
        response.setHeader("Connection", "keep-alive");
        response.setHeader("Keep-Alive", "timeout=120");
        response.setHeader("X-Content-Type-Options", "nosniff");

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        // Lấy user hiện tại từ session
        User currentUser = (User) session.getAttribute("currentUser");
        List<FlashcardsCategory> userFlcCategories = new ArrayList<>();
        if (currentUser != null) {
            // Lấy tất cả flccategory_id mà user này đã tạo flashcard
            userFlcCategories = flashcardsCategoryRepository.findAllByUserId(currentUser.getId());
        }
        model.addAttribute("userFlcCategories", userFlcCategories);

        if (categoryId != null) {
            List<Vocabulary> flashcards = vocabularyService.getVocabularyByCategoryId(categoryId);
            model.addAttribute("flashcards", flashcards);
            categories.stream()
                .filter(cat -> cat.getId() == categoryId)
                .findFirst()
                .ifPresent(cat -> model.addAttribute("selectedCategoryName", cat.getCategoryName()));
        } else if (flccategoryId != null) {
            // Lấy flashcard theo flccategoryId
            List<Flashcard> flashcards = flashcardRepository.findByFlccategoryId(flccategoryId);
            model.addAttribute("flashcards", flashcards);
            // Lấy tên bộ flashcard
            userFlcCategories.stream()
                .filter(flc -> flc.getFlccategoryId().equals(flccategoryId))
                .findFirst()
                .ifPresent(flc -> model.addAttribute("selectedCategoryName", flc.getFlccategoryName()));
        }
        return "flashcard";
    }

    @PostMapping("/flashcard-category-create")
    @ResponseBody
    public Map<String, Object> createFlashcardCategory(@RequestBody Map<String, String> body) {
        String name = body.get("flccategory_name");
        Map<String, Object> res = new HashMap<>();
        if (name == null || name.isEmpty()) {
            res.put("success", false);
            res.put("message", "Category name required");
            return res;
        }
        FlashcardsCategory cat = new FlashcardsCategory();
        cat.setFlccategoryName(name);
        flashcardsCategoryRepository.save(cat);
        res.put("success", true);
        res.put("flccategory_id", cat.getFlccategoryId());
        return res;
    }

    @PostMapping("/flashcard-add")
    @ResponseBody
    public Map<String, Object> addFlashcard(@RequestBody Map<String, String> body, HttpSession session) {
        Map<String, Object> res = new HashMap<>();
        String word = body.get("word");
        String definition = body.get("definition");
        String type = body.get("type"); // Lấy type từ request
        Integer flccategoryId = Integer.valueOf(body.get("flccategory_id"));
        User currentUser = (User) session.getAttribute("currentUser");
        if (word == null || definition == null || flccategoryId == null || currentUser == null) {
            res.put("success", false);
            res.put("message", "Missing data or user not logged in");
            return res;
        }
        Integer userId = currentUser.getId();

        Flashcard flashcard = new Flashcard();
        flashcard.setWord(word);
        flashcard.setDefinition(definition);
        flashcard.setFlccategoryId(flccategoryId);
        flashcard.setUserId(userId);
        flashcard.setVocabulary(null);
        flashcard.setPhonetic("");
        flashcard.setExample("");
        flashcard.setType(type != null ? type : ""); // Lưu type
        flashcard.setAudio("");
        flashcardRepository.save(flashcard);

        res.put("success", true);
        return res;
    }
}
