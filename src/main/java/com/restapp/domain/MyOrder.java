package com.restapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.restapp.domain.util.CustomDateTimeDeserializer;
import com.restapp.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A MyOrder.
 */
@Entity
@Table(name = "MYORDER")
public class MyOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "order_id")
    private Integer orderId;
    
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "session_id")
    private Integer sessionId;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "order_date")
    private DateTime orderDate;
    
    @Column(name = "order_value")
    private String orderValue;

    @OneToMany(mappedBy = "detailstoorder")
    @JsonIgnore
    private Set<MyOrderDetails> ordertodetailss = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public DateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(DateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(String orderValue) {
        this.orderValue = orderValue;
    }

    public Set<MyOrderDetails> getOrdertodetailss() {
        return ordertodetailss;
    }

    public void setOrdertodetailss(Set<MyOrderDetails> myOrderDetailss) {
        this.ordertodetailss = myOrderDetailss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MyOrder myOrder = (MyOrder) o;

        if ( ! Objects.equals(id, myOrder.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MyOrder{" +
                "id=" + id +
                ", orderId='" + orderId + "'" +
                ", userId='" + userId + "'" +
                ", sessionId='" + sessionId + "'" +
                ", orderDate='" + orderDate + "'" +
                ", orderValue='" + orderValue + "'" +
                '}';
    }
}
