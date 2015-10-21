package com.restapp.repository;

import com.restapp.domain.Shoppingcart;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Shoppingcart entity.
 */
public interface ShoppingcartRepository extends JpaRepository<Shoppingcart,Long> {

    @Query("select distinct shoppingcart from Shoppingcart shoppingcart left join fetch shoppingcart.hasmanys")
    List<Shoppingcart> findAllWithEagerRelationships();

    @Query("select shoppingcart from Shoppingcart shoppingcart left join fetch shoppingcart.hasmanys where shoppingcart.id =:id")
    Shoppingcart findOneWithEagerRelationships(@Param("id") Long id);

}
