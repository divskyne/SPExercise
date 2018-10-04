package com.qa.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.spring.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
	Page<Order> findByPersonId(Long personId, Pageable pageable);

}
