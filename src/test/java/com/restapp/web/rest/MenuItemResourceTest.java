package com.restapp.web.rest;

import com.restapp.Application;
import com.restapp.domain.MenuItem;
import com.restapp.repository.MenuItemRepository;

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
 * Test class for the MenuItemResource REST controller.
 *
 * @see MenuItemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MenuItemResourceTest {

    private static final String DEFAULT_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_TITLE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    private static final Double DEFAULT_COST = 1D;
    private static final Double UPDATED_COST = 2D;

    private static final Integer DEFAULT_CALORIES = 1;
    private static final Integer UPDATED_CALORIES = 2;
    private static final String DEFAULT_FOOD_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_FOOD_TYPE = "UPDATED_TEXT";

    @Inject
    private MenuItemRepository menuItemRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restMenuItemMockMvc;

    private MenuItem menuItem;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MenuItemResource menuItemResource = new MenuItemResource();
        ReflectionTestUtils.setField(menuItemResource, "menuItemRepository", menuItemRepository);
        this.restMenuItemMockMvc = MockMvcBuilders.standaloneSetup(menuItemResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        menuItem = new MenuItem();
        menuItem.setTitle(DEFAULT_TITLE);
        menuItem.setDescription(DEFAULT_DESCRIPTION);
        menuItem.setCost(DEFAULT_COST);
        menuItem.setCalories(DEFAULT_CALORIES);
        menuItem.setFoodType(DEFAULT_FOOD_TYPE);
    }

    @Test
    @Transactional
    public void createMenuItem() throws Exception {
        int databaseSizeBeforeCreate = menuItemRepository.findAll().size();

        // Create the MenuItem

        restMenuItemMockMvc.perform(post("/api/menuItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menuItem)))
                .andExpect(status().isCreated());

        // Validate the MenuItem in the database
        List<MenuItem> menuItems = menuItemRepository.findAll();
        assertThat(menuItems).hasSize(databaseSizeBeforeCreate + 1);
        MenuItem testMenuItem = menuItems.get(menuItems.size() - 1);
        assertThat(testMenuItem.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMenuItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMenuItem.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testMenuItem.getCalories()).isEqualTo(DEFAULT_CALORIES);
        assertThat(testMenuItem.getFoodType()).isEqualTo(DEFAULT_FOOD_TYPE);
    }

    @Test
    @Transactional
    public void getAllMenuItems() throws Exception {
        // Initialize the database
        menuItemRepository.saveAndFlush(menuItem);

        // Get all the menuItems
        restMenuItemMockMvc.perform(get("/api/menuItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(menuItem.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
                .andExpect(jsonPath("$.[*].calories").value(hasItem(DEFAULT_CALORIES)))
                .andExpect(jsonPath("$.[*].foodType").value(hasItem(DEFAULT_FOOD_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getMenuItem() throws Exception {
        // Initialize the database
        menuItemRepository.saveAndFlush(menuItem);

        // Get the menuItem
        restMenuItemMockMvc.perform(get("/api/menuItems/{id}", menuItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(menuItem.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.calories").value(DEFAULT_CALORIES))
            .andExpect(jsonPath("$.foodType").value(DEFAULT_FOOD_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMenuItem() throws Exception {
        // Get the menuItem
        restMenuItemMockMvc.perform(get("/api/menuItems/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMenuItem() throws Exception {
        // Initialize the database
        menuItemRepository.saveAndFlush(menuItem);

		int databaseSizeBeforeUpdate = menuItemRepository.findAll().size();

        // Update the menuItem
        menuItem.setTitle(UPDATED_TITLE);
        menuItem.setDescription(UPDATED_DESCRIPTION);
        menuItem.setCost(UPDATED_COST);
        menuItem.setCalories(UPDATED_CALORIES);
        menuItem.setFoodType(UPDATED_FOOD_TYPE);
        

        restMenuItemMockMvc.perform(put("/api/menuItems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menuItem)))
                .andExpect(status().isOk());

        // Validate the MenuItem in the database
        List<MenuItem> menuItems = menuItemRepository.findAll();
        assertThat(menuItems).hasSize(databaseSizeBeforeUpdate);
        MenuItem testMenuItem = menuItems.get(menuItems.size() - 1);
        assertThat(testMenuItem.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMenuItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMenuItem.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testMenuItem.getCalories()).isEqualTo(UPDATED_CALORIES);
        assertThat(testMenuItem.getFoodType()).isEqualTo(UPDATED_FOOD_TYPE);
    }

    @Test
    @Transactional
    public void deleteMenuItem() throws Exception {
        // Initialize the database
        menuItemRepository.saveAndFlush(menuItem);

		int databaseSizeBeforeDelete = menuItemRepository.findAll().size();

        // Get the menuItem
        restMenuItemMockMvc.perform(delete("/api/menuItems/{id}", menuItem.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MenuItem> menuItems = menuItemRepository.findAll();
        assertThat(menuItems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
