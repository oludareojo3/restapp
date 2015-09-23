package com.restapp.domain.shoppingcart;

import com.restapp.domain.menu.*;
import java.util.List;

public class ShoppingCart {
	
	private String userid;
	private String sessionid;
	private List<MenuItem> itemsInCart;
	
	public ShoppingCart(String uid, String sid, List<MenuItem> items)
	{
		this.userid = uid;
		this.sessionid = sid;
		this.itemsInCart = items;
	}
	
	public ShoppingCart() {
		// TODO Auto-generated constructor stub
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public List<MenuItem> getItemsInCart() {
		return itemsInCart;
	}

	public void setItemsInCart(List<MenuItem> itemsInCart) {
		this.itemsInCart = itemsInCart;
	}
	
}
