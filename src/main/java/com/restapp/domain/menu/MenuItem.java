package com.restapp.domain.menu;

public class MenuItem {

    private String title;
    private String description;
    private Double cost;

    public MenuItem(String title, String description, Double cost) {
        this.title = title;
        this.description = description;
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
