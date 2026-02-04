package com.example.users;

import org.springframework.web.bind.annotation.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import com.example.users.client.ExchangeClient;
import com.example.users.client.ExchangeRateDto;


import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final ExchangeClient exchangeClient;

	
	private final UserRepository repo;
	
	public UserController(UserRepository repo, ExchangeClient exchangeClient) {
	    this.repo = repo;
	    this.exchangeClient = exchangeClient;
	}
	
	@GetMapping("/rate")
	@CircuitBreaker(name = "exchangeService", fallbackMethod = "rateFallback")
	public String rate(@RequestParam String from, @RequestParam String to) {
	    ExchangeRateDto dto = exchangeClient.getRate(from, to);
	    return from + "->" + to + " rate = " + dto.rate;
	}

	public String rateFallback(String from, String to, Throwable t) {
	    return "Exchange service nije dostupan trenutno (fallback).";
	}
	
	// listamo sve korisnike
	@GetMapping
	public List<User> all(){
		return repo.findAll();
	}
	
	//dodajem korisnika
	@PostMapping
	public User create(@RequestBody User user) {
		
		if (user.getEmail() == null || user.getEmail().isBlank()) {
			throw new RuntimeException("Email is required");
		}
		
		if (user.getPassword() == null || user.getPassword().isBlank()) {
			throw new RuntimeException("Password is required");
			
		}
		
		if (user.getRole() == null ) {
			throw new RuntimeException("Role is required");
			
		}
		
		//mozemo samo jedan owner;
		if (user.getRole() == Role.OWNER && repo.existsByRole(Role.OWNER)) {
			throw new RuntimeException("Owner already exist");
		}
		
		return repo.save(user);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repo.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public User update(@PathVariable Long id, @RequestBody User newData) {

	    User existing = repo.findById(id)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    if (newData.getEmail() != null && !newData.getEmail().isBlank()) {
	        existing.setEmail(newData.getEmail());
	    }

	    if (newData.getPassword() != null && !newData.getPassword().isBlank()) {
	        existing.setPassword(newData.getPassword());
	    }

	    if (newData.getRole() != null) {
	        //  samno jedan owner
	        if (newData.getRole() == Role.OWNER && repo.existsByRole(Role.OWNER)) {
	            throw new RuntimeException("Owner already exists");
	        }
	        existing.setRole(newData.getRole());
	    }

	    return repo.save(existing);
	}

}
