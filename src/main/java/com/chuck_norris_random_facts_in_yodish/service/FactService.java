package com.chuck_norris_random_facts_in_yodish.service;

import com.chuck_norris_random_facts_in_yodish.model.Fact;
import com.chuck_norris_random_facts_in_yodish.repository.FactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FactService {

    @Autowired
    private FactRepository factRepository;

    public List<Fact> getAll() {
        return factRepository.findAll();
    }

    public Fact create(String text, LocalDate createdOn) {
        Fact fact = new Fact(text, createdOn);
        return factRepository.save(fact);
    }
}
