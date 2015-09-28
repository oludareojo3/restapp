package com.restapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A MenuItem.
 */
@Entity
@Table(name = "MENUITEM")
public class MenuItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "cost")
    private Double cost;
    
    @Column(name = "calories")
    private Integer calories;
    
    @Column(name = "food_type")
    private String foodType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenuItem menuItem = (MenuItem) o;

        if ( ! Objects.equals(id, menuItem.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", title='" + title + "'" +
                ", description='" + description + "'" +
                ", cost='" + cost + "'" +
                ", calories='" + calories + "'" +
                ", foodType='" + foodType + "'" +
                '}';
    }
}
