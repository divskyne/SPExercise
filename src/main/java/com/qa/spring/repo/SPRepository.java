package com.qa.spring.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.spring.model.SpringData;

@Repository
public interface SPRepository extends JpaRepository<SpringData,Long> {
	
	/**
	 * Returns the user with the given {@link name}.
	 *
	 * @param name can be {@literal null}.
	 * @return
	 */
	Optional<SpringData> findByName(String name);

}
