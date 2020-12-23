package com.chuck_norris_random_facts_in_yodish;


import com.chuck_norris_random_facts_in_yodish.model.Fact;
import com.chuck_norris_random_facts_in_yodish.service.FactService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FactTests {

    private String factText = "Creating a Fact with creation date";
    private LocalDate today = LocalDate.now();

    @Autowired
    private FactService service;

    @Test
    public void createValidFact() {
        Fact fact1 = new Fact(factText, today);
        Assert.assertNotNull(fact1);
        Assert.assertNull(fact1.getId());
    }

    @Test
    public void create2FactsAndComparingThem() {
        String factText = "Creating a Fact with creation date";
        LocalDate today = LocalDate.now();
        Fact fact1 = new Fact(factText, today);
        Assert.assertNotNull(fact1);
        Assert.assertNull(fact1.getId());

        Fact fact2 = service.create(factText, today);
        Assert.assertNotNull(fact2.getId());    //  pass, Id created when adding new Fact resource
        Assert.assertNotEquals(fact1, fact2);   //  pass, fact1 has no ID while fact2 has an ID assigned to it
    }

}
