package com.restapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.restapp.domain.Shoppingcart;
import com.restapp.repository.ShoppingcartRepository;
import com.restapp.web.rest.util.HeaderUtil;
import com.restapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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
 * REST controller for managing Shoppingcart.
 */
@RestController
@RequestMapping("/api")
public class ShoppingcartResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingcartResource.class);

    @Inject
    private ShoppingcartRepository shoppingcartRepository;

    /**
     * POST  /shoppingcarts -> Create a new shoppingcart.
     */
    @RequestMapping(value = "/shoppingcarts",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Shoppingcart> create(@RequestBody Shoppingcart shoppingcart) throws URISyntaxException {
        log.debug("REST request to save Shoppingcart : {}", shoppingcart);
        if (shoppingcart.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new shoppingcart cannot already have an ID").body(null);
        }
        Shoppingcart result = shoppingcartRepository.save(shoppingcart);
        return ResponseEntity.created(new URI("/api/shoppingcarts/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("shoppingcart", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /shoppingcarts -> Updates an existing shoppingcart.
     */
    @RequestMapping(value = "/shoppingcarts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Shoppingcart> update(@RequestBody Shoppingcart shoppingcart) throws URISyntaxException {
        log.debug("REST request to update Shoppingcart : {}", shoppingcart);
        if (shoppingcart.getId() == null) {
            return create(shoppingcart);
        }
        Shoppingcart result = shoppingcartRepository.save(shoppingcart);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("shoppingcart", shoppingcart.getId().toString()))
                .body(result);
    }

    /**
     * GET  /shoppingcarts -> get all the shoppingcarts.
     */
    @RequestMapping(value = "/shoppingcarts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Shoppingcart>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Shoppingcart> page = shoppingcartRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shoppingcarts", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shoppingcarts/:id -> get the "id" shoppingcart.
     */
    @RequestMapping(value = "/shoppingcarts/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Shoppingcart> get(@PathVariable Long id) {
        log.debug("REST request to get Shoppingcart : {}", id);
        return Optional.ofNullable(shoppingcartRepository.findOneWithEagerRelationships(id))
            .map(shoppingcart -> new ResponseEntity<>(
                shoppingcart,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /shoppingcarts/:id -> delete the "id" shoppingcart.
     */
    @RequestMapping(value = "/shoppingcarts/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Shoppingcart : {}", id);
        shoppingcartRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("shoppingcart", id.toString())).build();
    }
}
