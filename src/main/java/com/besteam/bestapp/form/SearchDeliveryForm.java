package com.besteam.bestapp.form;


import javax.validation.constraints.NotNull;

public class SearchDeliveryForm {
    //@NotNull
    private String from;
    //инждекс откуда куда - по возможности скачивать автоматческ икак на сайте почты рф
    //откуда город = подсказаьки,
    //куда город
    //вес граммы валидация
    //страна, регион, город - из почты россии стянуть полученеие данных
    //добавить валидацию
    //если не получилось сделать подсказки, сделать чтобы и индекс вводился вручную
    //если не получилось добавить поля для полного адреса страна край район город

    //@NotNull
    private String to;
    //@NotNull
    private String date;

    private String way;
    //@NotNull
    private String weight;

    private boolean filter;

    private boolean storyFilter;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public boolean isStoryFilter() {
        return storyFilter;
    }

    public void setStoryFilter(boolean storyFilter) {
        this.storyFilter = storyFilter;
    }
}
