package com.pbl5.pbl5.dto;

public class VocabTestResultDTO {
    private Long questionId;
    private String selectedOptionLabel;
    private boolean isCorrect;

    // Getters and Setters
    public Long getQuestionId() {
        return questionId;
    }
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
    public String getSelectedOptionLabel() {
        return selectedOptionLabel;
    }
    public void setSelectedOptionLabel(String selectedOptionLabel) {
        this.selectedOptionLabel = selectedOptionLabel;
    }
    public boolean isCorrect() {
        return isCorrect;
    }
    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}