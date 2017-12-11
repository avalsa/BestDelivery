package com.besteam.bestapp.form;

import com.besteam.bestapp.entity.Delivery;

import java.time.Duration;

public class SearchResultForm {

    String delivery;
    String cost;
    String time;

    public SearchResultForm(Delivery delivery, Integer cost, Duration time) {
        this.delivery = delivery != null ? delivery.toString() : null;
        this.cost = cost != null ? cost.toString() : null;
        this.time = time != null ? time.toString() : null;
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
