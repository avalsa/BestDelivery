package com.besteam.bestapp.form;

import com.besteam.bestapp.entity.Delivery;

import java.time.Duration;

public class SearchResultForm {

    String delivery;
    String cost;
    String time;

    public SearchResultForm(Delivery delivery, Integer cost, Duration time) {
        this.delivery = delivery.toString();
        this.cost = cost.toString();
        this.time = time.toString();
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
