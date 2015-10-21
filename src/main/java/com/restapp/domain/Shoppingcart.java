package com.restapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Shoppingcart.
 */
@Entity
@Table(name = "SHOPPINGCART")
public class Shoppingcart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "session_id")
    private String sessionId;
    
    @Column(name = "menu_item")
    private String menuItem;
    
    @Column(name = "current_user")
    private String currentUser;
    
    @Column(name = "total_cost")
    private String totalCost;

    @ManyToMany
    @JoinTable(name = "SHOPPINGCART_HASMANY",
               joinColumns = @JoinColumn(name="shoppingcarts_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="hasmanys_id", referencedColumnName="ID"))
    private Set<MenuItem> hasmanys = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public Set<MenuItem> getHasmanys() {
        return hasmanys;
    }

    public void setHasmanys(Set<MenuItem> menuItems) {
        this.hasmanys = menuItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Shoppingcart shoppingcart = (Shoppingcart) o;

        if ( ! Objects.equals(id, shoppingcart.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Shoppingcart{" +
                "id=" + id +
                ", sessionId='" + sessionId + "'" +
                ", menuItem='" + menuItem + "'" +
                ", currentUser='" + currentUser + "'" +
                ", totalCost='" + totalCost + "'" +
                '}';
    }
}
