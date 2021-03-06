package com.qa.spring.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.qa.spring.model.Order;
import com.qa.spring.repo.OrderRepo;
import com.qa.spring.repo.SPRepository;

@RestController
@RequestMapping("/api")
public class PersonController {
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private SPRepository myRepo;
	
	@GetMapping("/person/{personId}/orders")
	public Page<Order> getAllOrdersByPersonId(@PathVariable (value="personId") Long personId, Pageable pageable)
	{
		return orderRepo.findByPersonId(personId, pageable);
	}
	
	@PostMapping("/person/{personId}/orders")
	public Order createComment(@PathVariable (value="personId") Long personId,
			@Valid@RequestBody Order order)
	{
		return myRepo.findById(personId).map(myRepo -> {order.setPerson(myRepo);
		return orderRepo.save(order);}).orElseThrow(() -> new ResourceNotFoundException("Person", "id", order));
	}
	
	@PutMapping("/person/{personId}/orders/{orderId}")
	public Order updateOrder(@PathVariable (value="personId") Long personId,
			@PathVariable(value="orderId") Long orderId,
			@Valid@RequestBody Order orderRequest)
	{
		if(!myRepo.existsById(personId))
		{
			throw new ResourceNotFoundException("Person", "id", orderRequest);
		}
		return orderRepo.findById(orderId).map(order -> {order.setTitle(orderRequest.getTitle());
		return orderRepo.save(order);}).orElseThrow(() -> new ResourceNotFoundException("Order Id", "id", orderRequest));
	}
	
	@DeleteMapping("/person/{personId}/orders/{orderId}")
	public ResponseEntity<?> deleteComment (@PathVariable (value="personId") Long personId,
			@PathVariable(value="orderId") Long orderId)
	{
		if(!myRepo.existsById(personId))
		{
			throw new ResourceNotFoundException("Person", "id", personId);
		}
		return orderRepo.findById(orderId).map(order -> {orderRepo.delete(order);
		return ResponseEntity.ok().build();}).orElseThrow(() -> new ResourceNotFoundException("Order Id", orderId.toString(), null));

	}
}
