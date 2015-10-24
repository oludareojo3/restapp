package com.restapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A ShoppingCart.
 */
@Entity
@Table(name = "SHOPPINGCART")
public class ShoppingCart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "session_id")
    private Integer sessionId;

    @OneToMany(mappedBy = "shoppingcart")
    @JsonIgnore
    private Set<MyOrder> orders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public Set<MyOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<MyOrder> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShoppingCart shoppingCart = (ShoppingCart) o;

        if ( ! Objects.equals(id, shoppingCart.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", userId='" + userId + "'" +
                ", sessionId='" + sessionId + "'" +
                '}';
    }
}
