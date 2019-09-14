package com.chuck_norris_random_facts_in_yodish.controller;

import com.chuck_norris_random_facts_in_yodish.model.Fact;
import com.chuck_norris_random_facts_in_yodish.service.FactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    /**
     * Annotation {@code @CrossOrigin} allows to access one server from another,
     * e.g. accessing Java + Spring Boot + MongoDB server running on <a href="localhost:8080">local Host port 8080</a>
     * from angular that runs on <a href="localhost:4200">Local Host Port 4200</a>.
     * <a href="http://localhost:8080/fact/getAll">Rest API Request getAll </a>
     * @return
     */
    @CrossOrigin
    @RequestMapping("/fact/getAll")
    public List<Fact> getAll() {
        return factService.getAll();
    }

    /**
     * Annotation {@code @CrossOrigin} allows to access one server from another,
     * e.g. accessing Java + Spring Boot + MongoDB server running on <a href="localhost:8080">local Host port 8080</a>
     * from angular that runs on <a href="localhost:4200">Local Host Port 4200</a>.
     * <a href="http://localhost:8080/fact/create?text=Do or Not Do There Is No Try">Rest API Request Post (create)</a>
     * @param text
     * @return
     */
    @CrossOrigin
    @RequestMapping("/fact/create")
    public Fact create(@RequestParam String text) {
        LocalDate createdOn = LocalDate.now();
        return factService.create(text, createdOn);
    }
}
