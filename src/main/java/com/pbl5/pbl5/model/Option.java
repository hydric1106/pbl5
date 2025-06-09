package com.pbl5.pbl5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "option_label")
    private String optionLabel;

    @Column(name = "option_text")
    private String optionText;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    // Getters
    public Long getId() {
        return id;
    }

    public String getOptionLabel() {
        return optionLabel;
    }

    public String getOptionText() {
        return optionText;
    }

    public Question getQuestion() {
        return question;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setOptionLabel(String optionLabel) {
        this.optionLabel = optionLabel;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
