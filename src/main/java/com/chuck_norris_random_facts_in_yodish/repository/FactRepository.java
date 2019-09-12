package com.chuck_norris_random_facts_in_yodish.repository;

import com.chuck_norris_random_facts_in_yodish.model.Fact;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface FactRepository extends MongoRepository<Fact, String> {
    List<Fact> findByCreatedOn(LocalDate createdOn);
}
