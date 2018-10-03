package com.qa.spring.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.spring.exception.ResourceNotFoundException;
import com.qa.spring.model.SpringData;
import com.qa.spring.repo.SPRepository;

@RestController
@RequestMapping("/api")
public class SPController {
	
	@Autowired
	SPRepository litteRepo;
	
	@PostMapping("/person")
	private SpringData createPerson(@Valid @RequestBody SpringData m) {
		return litteRepo.save(m);
	}
	
	@GetMapping("/person/{id}")
	public SpringData getPersonbyID(@PathVariable(value="id")Long personID)
	{
		return litteRepo.findById(personID).orElseThrow(()->new ResourceNotFoundException("Model", "id", personID));
	}
	
	@GetMapping("/person")
	public List<SpringData> getAllPeople()
	{
		return litteRepo.findAll();
	}
	
	@PutMapping("/person/{id}")
	public SpringData updatePerson(@PathVariable(value="id")Long personID,
			@Valid@RequestBody SpringData personDetails)
	{
		SpringData e = litteRepo.findById(personID).orElseThrow(()->new ResourceNotFoundException("Model", "id", personID));
		
		e.setName(personDetails.getName());
		e.setAddress(personDetails.getAddress());
		e.setAge(personDetails.getAge());
		
		SpringData updateData = litteRepo.save(e);
		return updateData;
	}
	
	@DeleteMapping("/person/{id}")
	public ResponseEntity<?> deletePerson(@PathVariable(value="id")Long personID)
	{
		SpringData e = litteRepo.findById(personID).orElseThrow(()->new ResourceNotFoundException("Model", "id", personID));
		
		litteRepo.delete(e);
		return ResponseEntity.ok().build();
	}

}
