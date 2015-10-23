package com.restapp.repository;

import com.restapp.domain.MyOrderDetails;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MyOrderDetails entity.
 */
public interface MyOrderDetailsRepository extends JpaRepository<MyOrderDetails,Long> {

}
