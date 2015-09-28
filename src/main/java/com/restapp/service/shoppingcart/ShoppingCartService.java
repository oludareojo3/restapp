package com.restapp.service.shoppingcart;

import com.restapp.domain.MenuItem;
import com.restapp.domain.shoppingcart.ShoppingCart;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

	public void Checkout() {

	}

	public void Payment() {

	}

	public void AddItem(ShoppingCart cart, MenuItem item) {
		List<MenuItem> items = cart.getItemsInCart();
		items.add(item);
		System.out.println("Added a new item to the cart!");
	}

	public void RemoveItem(ShoppingCart cart, MenuItem item) {

		List<MenuItem> items = cart.getItemsInCart();

		int i = 0;
		while (i < items.size())
		{
			if(items.get(i) == item) {
				items.remove(i);
				System.out.println("Found item and removed it!");
			}
			else {
				i++;
			}
		}
	}

	public List<MenuItem> ShowItems(ShoppingCart cart) {

		List<MenuItem> menuItems = cart.getItemsInCart();

		return menuItems;
	}


}
