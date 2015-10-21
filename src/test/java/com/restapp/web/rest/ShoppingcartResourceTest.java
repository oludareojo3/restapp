package com.restapp.web.rest;

import com.restapp.Application;
import com.restapp.domain.Shoppingcart;
import com.restapp.repository.ShoppingcartRepository;

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
 * Test class for the ShoppingcartResource REST controller.
 *
 * @see ShoppingcartResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ShoppingcartResourceTest {

    private static final String DEFAULT_SESSION_ID = "SAMPLE_TEXT";
    private static final String UPDATED_SESSION_ID = "UPDATED_TEXT";
    private static final String DEFAULT_MENU_ITEM = "SAMPLE_TEXT";
    private static final String UPDATED_MENU_ITEM = "UPDATED_TEXT";
    private static final String DEFAULT_CURRENT_USER = "SAMPLE_TEXT";
    private static final String UPDATED_CURRENT_USER = "UPDATED_TEXT";
    private static final String DEFAULT_TOTAL_COST = "SAMPLE_TEXT";
    private static final String UPDATED_TOTAL_COST = "UPDATED_TEXT";

    @Inject
    private ShoppingcartRepository shoppingcartRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restShoppingcartMockMvc;

    private Shoppingcart shoppingcart;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShoppingcartResource shoppingcartResource = new ShoppingcartResource();
        ReflectionTestUtils.setField(shoppingcartResource, "shoppingcartRepository", shoppingcartRepository);
        this.restShoppingcartMockMvc = MockMvcBuilders.standaloneSetup(shoppingcartResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        shoppingcart = new Shoppingcart();
        shoppingcart.setSessionId(DEFAULT_SESSION_ID);
        shoppingcart.setMenuItem(DEFAULT_MENU_ITEM);
        shoppingcart.setCurrentUser(DEFAULT_CURRENT_USER);
        shoppingcart.setTotalCost(DEFAULT_TOTAL_COST);
    }

    @Test
    @Transactional
    public void createShoppingcart() throws Exception {
        int databaseSizeBeforeCreate = shoppingcartRepository.findAll().size();

        // Create the Shoppingcart

        restShoppingcartMockMvc.perform(post("/api/shoppingcarts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shoppingcart)))
                .andExpect(status().isCreated());

        // Validate the Shoppingcart in the database
        List<Shoppingcart> shoppingcarts = shoppingcartRepository.findAll();
        assertThat(shoppingcarts).hasSize(databaseSizeBeforeCreate + 1);
        Shoppingcart testShoppingcart = shoppingcarts.get(shoppingcarts.size() - 1);
        assertThat(testShoppingcart.getSessionId()).isEqualTo(DEFAULT_SESSION_ID);
        assertThat(testShoppingcart.getMenuItem()).isEqualTo(DEFAULT_MENU_ITEM);
        assertThat(testShoppingcart.getCurrentUser()).isEqualTo(DEFAULT_CURRENT_USER);
        assertThat(testShoppingcart.getTotalCost()).isEqualTo(DEFAULT_TOTAL_COST);
    }

    @Test
    @Transactional
    public void getAllShoppingcarts() throws Exception {
        // Initialize the database
        shoppingcartRepository.saveAndFlush(shoppingcart);

        // Get all the shoppingcarts
        restShoppingcartMockMvc.perform(get("/api/shoppingcarts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingcart.getId().intValue())))
                .andExpect(jsonPath("$.[*].sessionId").value(hasItem(DEFAULT_SESSION_ID.toString())))
                .andExpect(jsonPath("$.[*].menuItem").value(hasItem(DEFAULT_MENU_ITEM.toString())))
                .andExpect(jsonPath("$.[*].currentUser").value(hasItem(DEFAULT_CURRENT_USER.toString())))
                .andExpect(jsonPath("$.[*].totalCost").value(hasItem(DEFAULT_TOTAL_COST.toString())));
    }

    @Test
    @Transactional
    public void getShoppingcart() throws Exception {
        // Initialize the database
        shoppingcartRepository.saveAndFlush(shoppingcart);

        // Get the shoppingcart
        restShoppingcartMockMvc.perform(get("/api/shoppingcarts/{id}", shoppingcart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(shoppingcart.getId().intValue()))
            .andExpect(jsonPath("$.sessionId").value(DEFAULT_SESSION_ID.toString()))
            .andExpect(jsonPath("$.menuItem").value(DEFAULT_MENU_ITEM.toString()))
            .andExpect(jsonPath("$.currentUser").value(DEFAULT_CURRENT_USER.toString()))
            .andExpect(jsonPath("$.totalCost").value(DEFAULT_TOTAL_COST.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShoppingcart() throws Exception {
        // Get the shoppingcart
        restShoppingcartMockMvc.perform(get("/api/shoppingcarts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShoppingcart() throws Exception {
        // Initialize the database
        shoppingcartRepository.saveAndFlush(shoppingcart);

		int databaseSizeBeforeUpdate = shoppingcartRepository.findAll().size();

        // Update the shoppingcart
        shoppingcart.setSessionId(UPDATED_SESSION_ID);
        shoppingcart.setMenuItem(UPDATED_MENU_ITEM);
        shoppingcart.setCurrentUser(UPDATED_CURRENT_USER);
        shoppingcart.setTotalCost(UPDATED_TOTAL_COST);
        

        restShoppingcartMockMvc.perform(put("/api/shoppingcarts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(shoppingcart)))
                .andExpect(status().isOk());

        // Validate the Shoppingcart in the database
        List<Shoppingcart> shoppingcarts = shoppingcartRepository.findAll();
        assertThat(shoppingcarts).hasSize(databaseSizeBeforeUpdate);
        Shoppingcart testShoppingcart = shoppingcarts.get(shoppingcarts.size() - 1);
        assertThat(testShoppingcart.getSessionId()).isEqualTo(UPDATED_SESSION_ID);
        assertThat(testShoppingcart.getMenuItem()).isEqualTo(UPDATED_MENU_ITEM);
        assertThat(testShoppingcart.getCurrentUser()).isEqualTo(UPDATED_CURRENT_USER);
        assertThat(testShoppingcart.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
    }

    @Test
    @Transactional
    public void deleteShoppingcart() throws Exception {
        // Initialize the database
        shoppingcartRepository.saveAndFlush(shoppingcart);

		int databaseSizeBeforeDelete = shoppingcartRepository.findAll().size();

        // Get the shoppingcart
        restShoppingcartMockMvc.perform(delete("/api/shoppingcarts/{id}", shoppingcart.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Shoppingcart> shoppingcarts = shoppingcartRepository.findAll();
        assertThat(shoppingcarts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
