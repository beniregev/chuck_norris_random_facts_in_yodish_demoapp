package com.chuck_norris_random_facts_in_yodish.repository;

import com.chuck_norris_random_facts_in_yodish.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByName(String name);
}
