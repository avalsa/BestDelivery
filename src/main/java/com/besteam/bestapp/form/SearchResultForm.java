package com.besteam.bestapp.form;

import com.besteam.bestapp.entity.Delivery;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.time.Duration;

public class SearchResultForm {

    String delivery;
    String cost;
    String time;

    public SearchResultForm(Delivery delivery, Integer cost, Duration time) {
        this.delivery = delivery != null ? delivery.toString() : "N/A";
        this.cost = cost != null ? cost.toString() : "N/A";
        this.time = time != null ? DurationFormatUtils.formatDuration(time.toMillis(), "d' дн. 'H' ч.'") : "N/A";
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
