package com.restapp.repository;

import com.restapp.domain.ShoppingCart;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ShoppingCart entity.
 */
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {

}
