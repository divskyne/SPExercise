package com.qa.spring.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.qa.spring.DataBaseApplication;
import com.qa.spring.model.SpringData;
import com.qa.spring.repo.SPRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {DataBaseApplication.class})
@AutoConfigureMockMvc
public class IntegrationTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private SPRepository repository;
	
	@Before
	public void clearDB() {
		repository.deleteAll();
	}
	
	@After
	public void clearDBED() {
		repository.deleteAll();
	}
	
	@Test
	public void findingAndRetrievingPersonFromDatabase() throws Exception {
		repository.save(new SpringData("Dale", "Salford", 21));
		mvc.perform(get("/api/person")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].name", is("Dale")));
	}
	
	@Test
	public void addPersonToDatabaseTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/api/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Robert\",\"address\":\"Atlantis\",\"age\":200}"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.name", is("Robert")));
	}
	
	@Test
	public void findPerson5() throws Exception {
		addPersonToDatabaseTest();
		Long ID = repository.findByName("Robert").get().getId();
		mvc.perform(get("/api/person/"+ID)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.name", is("Robert")));
	}
	
	@Test
	public void deletePerson3() throws Exception {
		addPersonToDatabaseTest();
		Long ID = repository.findByName("Robert").get().getId();
		mvc.perform(delete("/api/person/"+ID)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	@Test
	public void putPersonToDatabaseTest4() throws Exception {
		addPersonToDatabaseTest();
		Long ID = repository.findByName("Robert").get().getId();
		mvc.perform(put("/api/person/"+ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Walberg\",\"address\":\"Atlantis\",\"age\":200}"))
		.andExpect(status().isOk());
	}
}
