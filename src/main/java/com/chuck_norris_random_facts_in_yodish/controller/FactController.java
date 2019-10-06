package com.chuck_norris_random_facts_in_yodish.controller;

import com.chuck_norris_random_facts_in_yodish.model.Fact;
import com.chuck_norris_random_facts_in_yodish.service.FactService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin
    @RequestMapping("/fact/createDemo")
    public String createDemo(@RequestParam String text) {
        return text;
    }

    /**
     * Delete a specific {@link Fact} resource. This request I added to delete facts which I added during my development.
     * @param factId of the fact to delete.
     * @return {@link Fact} resource taht was deletedd/removed.
     */
    @CrossOrigin
    @DeleteMapping("/fact/delete/{factId}")
    //@RequestMapping(value="/delete/{factId}", method=RequestMethod.DELETE)
    public Fact deleteById(@PathVariable String factId) {
        System.out.println("FactController.deleteById(" + factId + ")");
        Fact fact = factService.deleteById(factId);
        System.out.println("FactController.deleteById(" + factId + ") = " + fact);
        return fact;
    }

    @CrossOrigin
    @PostMapping("/fact/createFactInYodish")
    public ResponseEntity createYodishTextFromRandomFact() {
        System.out.println("FactController.createYodishTextFromRandomFact()");
        FactServiceResponseEntity response = factService.createYodishTextFromRandomFact();
        System.out.println("FactController.createYodishTextFromRandomFact() returned: " + response);

        ResponseEntity responseEntity;
        if (response.getStatusCode().value() < 200 || response.getStatusCode().value() > 299) {
            //  Error / Exception
            responseEntity = new ResponseEntity<>(response.getStatusMessage(), response.getStatusCode());
        } else {
            Gson gson = new Gson();
            String jsonFacts = gson.toJson(response.getFacts());
            System.out.println("jsonFacts = " + jsonFacts);
            responseEntity = new ResponseEntity<>(jsonFacts, response.getStatusCode());
        }
        return responseEntity;
    }
}
