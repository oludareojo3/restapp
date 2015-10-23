package com.restapp.web.rest;

import com.restapp.Application;
import com.restapp.domain.MyOrderDetails;
import com.restapp.repository.MyOrderDetailsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the MyOrderDetailsResource REST controller.
 *
 * @see MyOrderDetailsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MyOrderDetailsResourceTest {


    private static final Integer DEFAULT_ORDER_ID = 1;
    private static final Integer UPDATED_ORDER_ID = 2;

    private static final Integer DEFAULT_ITEM_ID = 1;
    private static final Integer UPDATED_ITEM_ID = 2;

    private static final Integer DEFAULT_QTY = 1;
    private static final Integer UPDATED_QTY = 2;

    private static final Integer DEFAULT_UNIT_PRICE = 1;
    private static final Integer UPDATED_UNIT_PRICE = 2;
    private static final String DEFAULT_ATTRIBUTE = "SAMPLE_TEXT";
    private static final String UPDATED_ATTRIBUTE = "UPDATED_TEXT";

    @Inject
    private MyOrderDetailsRepository myOrderDetailsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restMyOrderDetailsMockMvc;

    private MyOrderDetails myOrderDetails;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MyOrderDetailsResource myOrderDetailsResource = new MyOrderDetailsResource();
        ReflectionTestUtils.setField(myOrderDetailsResource, "myOrderDetailsRepository", myOrderDetailsRepository);
        this.restMyOrderDetailsMockMvc = MockMvcBuilders.standaloneSetup(myOrderDetailsResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        myOrderDetails = new MyOrderDetails();
        myOrderDetails.setOrderId(DEFAULT_ORDER_ID);
        myOrderDetails.setItemId(DEFAULT_ITEM_ID);
        myOrderDetails.setQty(DEFAULT_QTY);
        myOrderDetails.setUnitPrice(DEFAULT_UNIT_PRICE);
        myOrderDetails.setAttribute(DEFAULT_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void createMyOrderDetails() throws Exception {
        int databaseSizeBeforeCreate = myOrderDetailsRepository.findAll().size();

        // Create the MyOrderDetails

        restMyOrderDetailsMockMvc.perform(post("/api/myOrderDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myOrderDetails)))
                .andExpect(status().isCreated());

        // Validate the MyOrderDetails in the database
        List<MyOrderDetails> myOrderDetailss = myOrderDetailsRepository.findAll();
        assertThat(myOrderDetailss).hasSize(databaseSizeBeforeCreate + 1);
        MyOrderDetails testMyOrderDetails = myOrderDetailss.get(myOrderDetailss.size() - 1);
        assertThat(testMyOrderDetails.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testMyOrderDetails.getItemId()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testMyOrderDetails.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testMyOrderDetails.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testMyOrderDetails.getAttribute()).isEqualTo(DEFAULT_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void getAllMyOrderDetailss() throws Exception {
        // Initialize the database
        myOrderDetailsRepository.saveAndFlush(myOrderDetails);

        // Get all the myOrderDetailss
        restMyOrderDetailsMockMvc.perform(get("/api/myOrderDetailss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(myOrderDetails.getId().intValue())))
                .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID)))
                .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID)))
                .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
                .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE)))
                .andExpect(jsonPath("$.[*].attribute").value(hasItem(DEFAULT_ATTRIBUTE.toString())));
    }

    @Test
    @Transactional
    public void getMyOrderDetails() throws Exception {
        // Initialize the database
        myOrderDetailsRepository.saveAndFlush(myOrderDetails);

        // Get the myOrderDetails
        restMyOrderDetailsMockMvc.perform(get("/api/myOrderDetailss/{id}", myOrderDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(myOrderDetails.getId().intValue()))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.itemId").value(DEFAULT_ITEM_ID))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE))
            .andExpect(jsonPath("$.attribute").value(DEFAULT_ATTRIBUTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMyOrderDetails() throws Exception {
        // Get the myOrderDetails
        restMyOrderDetailsMockMvc.perform(get("/api/myOrderDetailss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyOrderDetails() throws Exception {
        // Initialize the database
        myOrderDetailsRepository.saveAndFlush(myOrderDetails);

		int databaseSizeBeforeUpdate = myOrderDetailsRepository.findAll().size();

        // Update the myOrderDetails
        myOrderDetails.setOrderId(UPDATED_ORDER_ID);
        myOrderDetails.setItemId(UPDATED_ITEM_ID);
        myOrderDetails.setQty(UPDATED_QTY);
        myOrderDetails.setUnitPrice(UPDATED_UNIT_PRICE);
        myOrderDetails.setAttribute(UPDATED_ATTRIBUTE);
        

        restMyOrderDetailsMockMvc.perform(put("/api/myOrderDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myOrderDetails)))
                .andExpect(status().isOk());

        // Validate the MyOrderDetails in the database
        List<MyOrderDetails> myOrderDetailss = myOrderDetailsRepository.findAll();
        assertThat(myOrderDetailss).hasSize(databaseSizeBeforeUpdate);
        MyOrderDetails testMyOrderDetails = myOrderDetailss.get(myOrderDetailss.size() - 1);
        assertThat(testMyOrderDetails.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testMyOrderDetails.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testMyOrderDetails.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testMyOrderDetails.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testMyOrderDetails.getAttribute()).isEqualTo(UPDATED_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void deleteMyOrderDetails() throws Exception {
        // Initialize the database
        myOrderDetailsRepository.saveAndFlush(myOrderDetails);

		int databaseSizeBeforeDelete = myOrderDetailsRepository.findAll().size();

        // Get the myOrderDetails
        restMyOrderDetailsMockMvc.perform(delete("/api/myOrderDetailss/{id}", myOrderDetails.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MyOrderDetails> myOrderDetailss = myOrderDetailsRepository.findAll();
        assertThat(myOrderDetailss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
