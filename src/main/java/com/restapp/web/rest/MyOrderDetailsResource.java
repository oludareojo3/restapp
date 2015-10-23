package com.restapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.restapp.domain.MyOrderDetails;
import com.restapp.repository.MyOrderDetailsRepository;
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
 * REST controller for managing MyOrderDetails.
 */
@RestController
@RequestMapping("/api")
public class MyOrderDetailsResource {

    private final Logger log = LoggerFactory.getLogger(MyOrderDetailsResource.class);

    @Inject
    private MyOrderDetailsRepository myOrderDetailsRepository;

    /**
     * POST  /myOrderDetailss -> Create a new myOrderDetails.
     */
    @RequestMapping(value = "/myOrderDetailss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MyOrderDetails> create(@RequestBody MyOrderDetails myOrderDetails) throws URISyntaxException {
        log.debug("REST request to save MyOrderDetails : {}", myOrderDetails);
        if (myOrderDetails.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new myOrderDetails cannot already have an ID").body(null);
        }
        MyOrderDetails result = myOrderDetailsRepository.save(myOrderDetails);
        return ResponseEntity.created(new URI("/api/myOrderDetailss/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("myOrderDetails", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /myOrderDetailss -> Updates an existing myOrderDetails.
     */
    @RequestMapping(value = "/myOrderDetailss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MyOrderDetails> update(@RequestBody MyOrderDetails myOrderDetails) throws URISyntaxException {
        log.debug("REST request to update MyOrderDetails : {}", myOrderDetails);
        if (myOrderDetails.getId() == null) {
            return create(myOrderDetails);
        }
        MyOrderDetails result = myOrderDetailsRepository.save(myOrderDetails);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("myOrderDetails", myOrderDetails.getId().toString()))
                .body(result);
    }

    /**
     * GET  /myOrderDetailss -> get all the myOrderDetailss.
     */
    @RequestMapping(value = "/myOrderDetailss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MyOrderDetails>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<MyOrderDetails> page = myOrderDetailsRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/myOrderDetailss", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /myOrderDetailss/:id -> get the "id" myOrderDetails.
     */
    @RequestMapping(value = "/myOrderDetailss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MyOrderDetails> get(@PathVariable Long id) {
        log.debug("REST request to get MyOrderDetails : {}", id);
        return Optional.ofNullable(myOrderDetailsRepository.findOne(id))
            .map(myOrderDetails -> new ResponseEntity<>(
                myOrderDetails,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /myOrderDetailss/:id -> delete the "id" myOrderDetails.
     */
    @RequestMapping(value = "/myOrderDetailss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete MyOrderDetails : {}", id);
        myOrderDetailsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("myOrderDetails", id.toString())).build();
    }
}
