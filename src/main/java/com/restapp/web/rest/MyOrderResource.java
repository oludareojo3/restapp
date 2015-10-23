package com.restapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.restapp.domain.MyOrder;
import com.restapp.repository.MyOrderRepository;
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
 * REST controller for managing MyOrder.
 */
@RestController
@RequestMapping("/api")
public class MyOrderResource {

    private final Logger log = LoggerFactory.getLogger(MyOrderResource.class);

    @Inject
    private MyOrderRepository myOrderRepository;

    /**
     * POST  /myOrders -> Create a new myOrder.
     */
    @RequestMapping(value = "/myOrders",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MyOrder> create(@RequestBody MyOrder myOrder) throws URISyntaxException {
        log.debug("REST request to save MyOrder : {}", myOrder);
        if (myOrder.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new myOrder cannot already have an ID").body(null);
        }
        MyOrder result = myOrderRepository.save(myOrder);
        return ResponseEntity.created(new URI("/api/myOrders/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("myOrder", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /myOrders -> Updates an existing myOrder.
     */
    @RequestMapping(value = "/myOrders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MyOrder> update(@RequestBody MyOrder myOrder) throws URISyntaxException {
        log.debug("REST request to update MyOrder : {}", myOrder);
        if (myOrder.getId() == null) {
            return create(myOrder);
        }
        MyOrder result = myOrderRepository.save(myOrder);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("myOrder", myOrder.getId().toString()))
                .body(result);
    }

    /**
     * GET  /myOrders -> get all the myOrders.
     */
    @RequestMapping(value = "/myOrders",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MyOrder>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<MyOrder> page = myOrderRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/myOrders", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /myOrders/:id -> get the "id" myOrder.
     */
    @RequestMapping(value = "/myOrders/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MyOrder> get(@PathVariable Long id) {
        log.debug("REST request to get MyOrder : {}", id);
        return Optional.ofNullable(myOrderRepository.findOne(id))
            .map(myOrder -> new ResponseEntity<>(
                myOrder,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /myOrders/:id -> delete the "id" myOrder.
     */
    @RequestMapping(value = "/myOrders/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete MyOrder : {}", id);
        myOrderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("myOrder", id.toString())).build();
    }
}
