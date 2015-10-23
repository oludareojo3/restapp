package com.restapp.web.rest;

import com.restapp.Application;
import com.restapp.domain.MyOrder;
import com.restapp.repository.MyOrderRepository;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the MyOrderResource REST controller.
 *
 * @see MyOrderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MyOrderResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final Integer DEFAULT_ORDER_ID = 1;
    private static final Integer UPDATED_ORDER_ID = 2;

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final Integer DEFAULT_SESSION_ID = 1;
    private static final Integer UPDATED_SESSION_ID = 2;

    private static final DateTime DEFAULT_ORDER_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_ORDER_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_ORDER_DATE_STR = dateTimeFormatter.print(DEFAULT_ORDER_DATE);
    private static final String DEFAULT_ORDER_VALUE = "SAMPLE_TEXT";
    private static final String UPDATED_ORDER_VALUE = "UPDATED_TEXT";

    @Inject
    private MyOrderRepository myOrderRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restMyOrderMockMvc;

    private MyOrder myOrder;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MyOrderResource myOrderResource = new MyOrderResource();
        ReflectionTestUtils.setField(myOrderResource, "myOrderRepository", myOrderRepository);
        this.restMyOrderMockMvc = MockMvcBuilders.standaloneSetup(myOrderResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        myOrder = new MyOrder();
        myOrder.setOrderId(DEFAULT_ORDER_ID);
        myOrder.setUserId(DEFAULT_USER_ID);
        myOrder.setSessionId(DEFAULT_SESSION_ID);
        myOrder.setOrderDate(DEFAULT_ORDER_DATE);
        myOrder.setOrderValue(DEFAULT_ORDER_VALUE);
    }

    @Test
    @Transactional
    public void createMyOrder() throws Exception {
        int databaseSizeBeforeCreate = myOrderRepository.findAll().size();

        // Create the MyOrder

        restMyOrderMockMvc.perform(post("/api/myOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myOrder)))
                .andExpect(status().isCreated());

        // Validate the MyOrder in the database
        List<MyOrder> myOrders = myOrderRepository.findAll();
        assertThat(myOrders).hasSize(databaseSizeBeforeCreate + 1);
        MyOrder testMyOrder = myOrders.get(myOrders.size() - 1);
        assertThat(testMyOrder.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testMyOrder.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testMyOrder.getSessionId()).isEqualTo(DEFAULT_SESSION_ID);
        assertThat(testMyOrder.getOrderDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testMyOrder.getOrderValue()).isEqualTo(DEFAULT_ORDER_VALUE);
    }

    @Test
    @Transactional
    public void getAllMyOrders() throws Exception {
        // Initialize the database
        myOrderRepository.saveAndFlush(myOrder);

        // Get all the myOrders
        restMyOrderMockMvc.perform(get("/api/myOrders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(myOrder.getId().intValue())))
                .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID)))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
                .andExpect(jsonPath("$.[*].sessionId").value(hasItem(DEFAULT_SESSION_ID)))
                .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE_STR)))
                .andExpect(jsonPath("$.[*].orderValue").value(hasItem(DEFAULT_ORDER_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getMyOrder() throws Exception {
        // Initialize the database
        myOrderRepository.saveAndFlush(myOrder);

        // Get the myOrder
        restMyOrderMockMvc.perform(get("/api/myOrders/{id}", myOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(myOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.sessionId").value(DEFAULT_SESSION_ID))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE_STR))
            .andExpect(jsonPath("$.orderValue").value(DEFAULT_ORDER_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMyOrder() throws Exception {
        // Get the myOrder
        restMyOrderMockMvc.perform(get("/api/myOrders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyOrder() throws Exception {
        // Initialize the database
        myOrderRepository.saveAndFlush(myOrder);

		int databaseSizeBeforeUpdate = myOrderRepository.findAll().size();

        // Update the myOrder
        myOrder.setOrderId(UPDATED_ORDER_ID);
        myOrder.setUserId(UPDATED_USER_ID);
        myOrder.setSessionId(UPDATED_SESSION_ID);
        myOrder.setOrderDate(UPDATED_ORDER_DATE);
        myOrder.setOrderValue(UPDATED_ORDER_VALUE);
        

        restMyOrderMockMvc.perform(put("/api/myOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myOrder)))
                .andExpect(status().isOk());

        // Validate the MyOrder in the database
        List<MyOrder> myOrders = myOrderRepository.findAll();
        assertThat(myOrders).hasSize(databaseSizeBeforeUpdate);
        MyOrder testMyOrder = myOrders.get(myOrders.size() - 1);
        assertThat(testMyOrder.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testMyOrder.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testMyOrder.getSessionId()).isEqualTo(UPDATED_SESSION_ID);
        assertThat(testMyOrder.getOrderDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testMyOrder.getOrderValue()).isEqualTo(UPDATED_ORDER_VALUE);
    }

    @Test
    @Transactional
    public void deleteMyOrder() throws Exception {
        // Initialize the database
        myOrderRepository.saveAndFlush(myOrder);

		int databaseSizeBeforeDelete = myOrderRepository.findAll().size();

        // Get the myOrder
        restMyOrderMockMvc.perform(delete("/api/myOrders/{id}", myOrder.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MyOrder> myOrders = myOrderRepository.findAll();
        assertThat(myOrders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
