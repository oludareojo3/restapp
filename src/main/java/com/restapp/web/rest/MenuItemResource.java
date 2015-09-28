package com.restapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.restapp.domain.MenuItem;
import com.restapp.repository.MenuItemRepository;
import com.restapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MenuItem.
 */
@RestController
@RequestMapping("/api")
public class MenuItemResource {

    private final Logger log = LoggerFactory.getLogger(MenuItemResource.class);

    @Inject
    private MenuItemRepository menuItemRepository;

    /**
     * POST  /menuItems -> Create a new menuItem.
     */
    @RequestMapping(value = "/menuItems",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MenuItem> create(@RequestBody MenuItem menuItem) throws URISyntaxException {
        log.debug("REST request to save MenuItem : {}", menuItem);
        if (menuItem.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new menuItem cannot already have an ID").body(null);
        }
        MenuItem result = menuItemRepository.save(menuItem);
        return ResponseEntity.created(new URI("/api/menuItems/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("menuItem", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /menuItems -> Updates an existing menuItem.
     */
    @RequestMapping(value = "/menuItems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MenuItem> update(@RequestBody MenuItem menuItem) throws URISyntaxException {
        log.debug("REST request to update MenuItem : {}", menuItem);
        if (menuItem.getId() == null) {
            return create(menuItem);
        }
        MenuItem result = menuItemRepository.save(menuItem);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("menuItem", menuItem.getId().toString()))
                .body(result);
    }

    /**
     * GET  /menuItems -> get all the menuItems.
     */
    @RequestMapping(value = "/menuItems",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MenuItem> getAll() {
        log.debug("REST request to get all MenuItems");
        return menuItemRepository.findAll();
    }

    /**
     * GET  /menuItems/:id -> get the "id" menuItem.
     */
    @RequestMapping(value = "/menuItems/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MenuItem> get(@PathVariable Long id) {
        log.debug("REST request to get MenuItem : {}", id);
        return Optional.ofNullable(menuItemRepository.findOne(id))
            .map(menuItem -> new ResponseEntity<>(
                menuItem,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /menuItems/:id -> delete the "id" menuItem.
     */
    @RequestMapping(value = "/menuItems/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete MenuItem : {}", id);
        menuItemRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("menuItem", id.toString())).build();
    }
}
