package com.restapp.service.menu;

import com.restapp.domain.menu.MenuItem;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuItemService {

    public List<MenuItem> getMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(new MenuItem("Chicken", "Some Chicken Dish", Double.valueOf("12")));
        menuItems.add(new MenuItem("Fish", "Some Fish Dish", Double.valueOf("14")));
        menuItems.add(new MenuItem("Pasta", "Some Pasta Dish", Double.valueOf("10")));

        return menuItems;
    }

}
