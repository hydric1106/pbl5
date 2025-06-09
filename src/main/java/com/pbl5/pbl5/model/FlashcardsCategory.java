package com.pbl5.pbl5.model;

import jakarta.persistence.*;

@Entity
@Table(name = "flashcards_categories")
public class FlashcardsCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flccategory_id")
    private Integer flccategoryId;

    @Column(name = "flccategory_name")
    private String flccategoryName;

    public Integer getFlccategoryId() {
        return flccategoryId;
    }

    public void setFlccategoryId(Integer flccategoryId) {
        this.flccategoryId = flccategoryId;
    }

    public String getFlccategoryName() {
        return flccategoryName;
    }

    public void setFlccategoryName(String flccategoryName) {
        this.flccategoryName = flccategoryName;
    }
}