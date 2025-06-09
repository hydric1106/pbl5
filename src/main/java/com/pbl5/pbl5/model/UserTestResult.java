package com.pbl5.pbl5.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_test_results")
public class UserTestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "selected_option_label")
    private String selectedOptionLabel;

    @Column(name = "selected_answer_text")
    private String selectedAnswerText;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "score_obtained")
    private Integer scoreObtained;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getSelectedOptionLabel() {
        return selectedOptionLabel;
    }

    public void setSelectedOptionLabel(String selectedOptionLabel) {
        this.selectedOptionLabel = selectedOptionLabel;
    }

    public String getSelectedAnswerText() {
        return selectedAnswerText;
    }

    public void setSelectedAnswerText(String selectedAnswerText) {
        this.selectedAnswerText = selectedAnswerText;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getScoreObtained() {
        return scoreObtained;
    }

    public void setScoreObtained(Integer scoreObtained) {
        this.scoreObtained = scoreObtained;
    }

}