package com.restapp.web.rest.menu;

import com.restapp.domain.menu.MenuItem;
import com.restapp.service.menu.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuItemResource {

    @Autowired
    MenuItemService menuItemService;

    @RequestMapping(method = RequestMethod.GET)
    public List<MenuItem> getAllItems() {
        return menuItemService.getMenuItems();
    }

}
