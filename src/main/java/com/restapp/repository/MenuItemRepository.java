package com.restapp.repository;

import com.restapp.domain.MenuItem;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MenuItem entity.
 */
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {

}
