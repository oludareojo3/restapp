package com.restapp.repository;

import com.restapp.domain.MyOrder;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MyOrder entity.
 */
public interface MyOrderRepository extends JpaRepository<MyOrder,Long> {

}
