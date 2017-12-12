package com.besteam.bestapp.form;


import javax.validation.constraints.NotNull;

public class SearchDeliveryForm {

    private String from;
    @NotNull
    private String to;
    @NotNull
    private String date;

    private String way;
    @NotNull
    private String weight;
    @NotNull
    private String height;
    @NotNull
    private String length;
    @NotNull
    private String width;

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

    public boolean getFilter() {
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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getFromCity(){
        return from.split(", ")[1].trim();
    }

    public String getFromCountry(){
        return from.split(", ")[0].trim();
    }

    public String getToCity(){
        return to.split(", ")[1].trim();
    }

    public String getToCountry(){
        return to.split(", ")[0].trim();
    }
}
