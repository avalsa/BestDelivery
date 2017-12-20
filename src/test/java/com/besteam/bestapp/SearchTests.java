package com.besteam.bestapp;

import com.besteam.bestapp.form.SearchDeliveryForm;
import com.besteam.bestapp.service.search.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.assertNotNull;

public class SearchTests {
    private SearchDeliveryForm form;


    @Before
    public void setUp() {
        form = new SearchDeliveryForm();
        form.setFrom("Россия, Москва");
        form.setTo("Россия, Краснодар");
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
    public void testDpdSearch() {
        SearchDeliveryResult r = new DPDSearch().doRequest(form);
        assertNotNull(r.getCost());
    }

    @Test
    public void testAll() {
        SearchDeliveryResults r = new SearchDeliveryService().doRequest(form);
        r.getResults().forEach(Assert::assertNotNull);
    }

    @Test
    public void testCityCoords() {
        Point2D.Double p = new LocationInfoHelpersRequests().getCoordinatesByCityName("Москва");
        assertNotNull(p);
    }

    @Test
    public void testCityPostalCode() {
        String code = new LocationInfoHelpersRequests().getPostalCodeByCoordinates(new Point2D.Double(37.622504, 55.753215));
        assertNotNull(code);
    }

    @Test
    public void testCityPostalCode2() {
        String code = new LocationInfoHelpersRequests().getPostalCodeByCityName("Москва");
        assertNotNull(code);
    }
}
