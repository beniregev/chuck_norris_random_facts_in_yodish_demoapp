package com.chuck_norris_random_facts_in_yodish.controller;

import com.chuck_norris_random_facts_in_yodish.model.Fact;
import com.chuck_norris_random_facts_in_yodish.service.FactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Boot annotations for handling different HTTP request types:
 * @RequestMapping — For handling any request type.
 * @GetMapping — For a GET request.
 * @PostMapping — For a POST request.
 * @PutMapping — For a PUT request.
 * @PatchMapping — For a PATCH request.
 * @DeleteMapping — For a DELETE request.
 * To make a POST request, the method handling the request will be annotated like this:
 * @RequestMapping(value = "path", method = RequestMethod.POST)
 * public returnType methodName(){...}
 * Or like this:
 * @PostMapping("path")
 * public returnType methodName(){...}
 */
@RestController
public class FactController {

    @Autowired
    private FactService factService;

    @RequestMapping("/fact/getAll")
    public List<Fact> getAll() {
        return factService.getAll();
    }

    @RequestMapping("/fact/create")
    public Fact create(@RequestParam String text) {
        LocalDate createdOn = LocalDate.now();
        return factService.create(text, createdOn);
    }

}
