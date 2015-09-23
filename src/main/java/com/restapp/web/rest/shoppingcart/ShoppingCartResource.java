package com.restapp.web.rest.shoppingcart;

import com.restapp.domain.menu.MenuItem;
import com.restapp.domain.shoppingcart.ShoppingCart;
import com.restapp.service.shoppingcart.ShoppingCartService;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;  
  
@RestController  
@RequestMapping("/api/shoppingcart")  

public class ShoppingCartResource {

	@Autowired
	ShoppingCartService shoppingCartService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<MenuItem> getMyCart() {
		
		List<MenuItem> myItems = new ArrayList<>();
		
		myItems.add(new MenuItem("Spaghetti with Meatballs", "Italian", Double.valueOf("12")));

		ShoppingCart cart = new ShoppingCart("lindseylanier","12345", myItems);
		
		return shoppingCartService.ShowItems(cart);
	}
	
}
