package com.restapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A MyOrderDetails.
 */
@Entity
@Table(name = "MYORDERDETAILS")
public class MyOrderDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "order_id")
    private Integer orderId;
    
    @Column(name = "item_id")
    private Integer itemId;
    
    @Column(name = "qty")
    private Integer qty;
    
    @Column(name = "unit_price")
    private Integer unitPrice;
    
    @Column(name = "attribute")
    private String attribute;

    @ManyToOne
    private MyOrder detailstoorder;

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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public MyOrder getDetailstoorder() {
        return detailstoorder;
    }

    public void setDetailstoorder(MyOrder myOrder) {
        this.detailstoorder = myOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MyOrderDetails myOrderDetails = (MyOrderDetails) o;

        if ( ! Objects.equals(id, myOrderDetails.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MyOrderDetails{" +
                "id=" + id +
                ", orderId='" + orderId + "'" +
                ", itemId='" + itemId + "'" +
                ", qty='" + qty + "'" +
                ", unitPrice='" + unitPrice + "'" +
                ", attribute='" + attribute + "'" +
                '}';
    }
}
