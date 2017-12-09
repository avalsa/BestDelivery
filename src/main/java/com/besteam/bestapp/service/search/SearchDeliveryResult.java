package com.besteam.bestapp.service.search;

import com.besteam.bestapp.entity.Delivery;

import java.time.Duration;

public class SearchDeliveryResult {
    private Delivery delivery;
    private Integer cost;
    private Duration deliveryTime;

    private boolean aviaAvailable;
    private Duration minTime;
    private Duration maxTime;

    public SearchDeliveryResult(Delivery d) {

    }


    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Duration getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Duration deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Duration getMinTime() {
        return minTime;
    }

    public void setMinTime(Duration minTime) {
        this.minTime = minTime;
    }

    public Duration getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Duration maxTime) {
        this.maxTime = maxTime;
    }

    public boolean isAviaAvailable() {
        return aviaAvailable;
    }

    public void setAviaAvailable(boolean aviaAvailable) {
        this.aviaAvailable = aviaAvailable;
    }
}
