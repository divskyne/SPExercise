package com.qa.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.spring.model.SpringData;

@Repository
public interface SPRepository extends JpaRepository<SpringData,Long> {

}
