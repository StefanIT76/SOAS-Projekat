package com.example.users;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserRepository repo;
	
	public UserController(UserRepository repo) {
		this.repo = repo;
		
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
