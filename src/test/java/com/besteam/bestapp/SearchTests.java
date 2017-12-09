package com.besteam.bestapp;

import com.besteam.bestapp.form.SearchDeliveryForm;
import com.besteam.bestapp.service.search.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SearchTests {
    private SearchDeliveryForm form;


    @Before
    public void setUp() {
        form = new SearchDeliveryForm();
        form.setFrom("Москва");
        form.setTo("Краснодар");
        form.setHeight("10");
        form.setWidth("10");
        form.setLength("2");
        form.setWeight("1000");
    }

    @Test
    public void testPostRfSearch() {
        SearchDeliveryResult r = new PostRfSearch().doRequest(form);
        assertNotNull(r.getCost());
    }

    @Test
    public void testBoxberrySearch() {
        SearchDeliveryResult r = new BoxberryDeliverySearch().doRequest(form);
        assertNotNull(r.getCost());
    }

    @Test
    public void testImlSearch() {
        SearchDeliveryResult r = new ImlDeliverySearch().doRequest(form);
        assertNotNull(r.getCost());
    }

    @Test
    public void testAll() {
        SearchDeliveryResults r = new SearchDeliveryService().doRequest(form);
        r.getResults().forEach(Assert::assertNotNull);
    }
}
