package com.chuck_norris_random_facts_in_yodish.controller;

import com.chuck_norris_random_facts_in_yodish.model.Fact;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;

public class FactServiceResponseEntity {
    private HttpStatus statusCode;
    private String statusMessage;
    private List<Fact> facts;

    public FactServiceResponseEntity() {
    }

    public FactServiceResponseEntity(List<Fact> facts) {
        this.facts = facts;
    }

    public FactServiceResponseEntity(HttpStatus statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public FactServiceResponseEntity(HttpStatus statusCode, String statusMessage, List<Fact> facts) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.facts = facts;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<Fact> getFacts() {
        return facts;
    }

    public void setFacts(List<Fact> facts) {
        this.facts = facts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactServiceResponseEntity that = (FactServiceResponseEntity) o;
        return statusCode == that.statusCode &&
                statusMessage.equals(that.statusMessage) &&
                Objects.equals(facts, that.facts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, statusMessage, facts);
    }

    @Override
    public String toString() {
        return "FactServiceResponseEntity{" +
                "statusCode=" + statusCode +
                ", statusMessage='" + statusMessage + '\'' +
                ", facts=" + facts +
                '}';
    }
}
