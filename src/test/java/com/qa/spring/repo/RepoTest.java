package com.qa.spring.repo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.qa.spring.model.SpringData;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepoTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private SPRepository myRepo;
	
	@Test
	private void retrieveByIDTest() {
		SpringData model1 = new SpringData("Bob", "Space", 12);
		entityManager.persist(model1);
		entityManager.flush();
		assertTrue(myRepo.findById(model1.getId()).isPresent());
	}

}
