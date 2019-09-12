package com.chuck_norris_random_facts_in_yodish.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
public class Fact {
    @Id
    private String id;
    private String text;
    private LocalDate createdOn;

    public Fact(String text, LocalDate createdOn) {
        this.text = text;
        this.createdOn = createdOn;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "Fact{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}
