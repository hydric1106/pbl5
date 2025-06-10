package com.pbl5.pbl5.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "flashcards")
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "vocabulary_id", nullable = true)
    private Vocabulary vocabulary;

    @Column(name = "word", length = 99, nullable = false)
    private String word;

    @Column(name = "definition", columnDefinition = "TEXT", nullable = false)
    private String definition;

    @Column(name = "phonetic", length = 99, nullable = false)
    private String phonetic;

    @Column(name = "example", columnDefinition = "TEXT", nullable = false)
    private String example;

    @Column(name = "type", length = 99, nullable = false)
    private String type;

    @Column(name = "audio", columnDefinition = "TEXT", nullable = false)
    private String audio;

    @Column(name = "flccategory_id")
    private Integer flccategoryId;

    @Column(name = "user_id")
    private Integer userId;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFlccategoryId() {
        return flccategoryId;
    }

    public void setFlccategoryId(Integer flccategoryId) {
        this.flccategoryId = flccategoryId;
    }
}