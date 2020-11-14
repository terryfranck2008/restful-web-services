package com.francktepi.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserRessources {
	
	@Autowired
	private UserDaoService userService;
	
	@GetMapping(path="/users")
	public List<User> retrieveAllUsers(){
		return userService.findAll();
	}
	
	@GetMapping(path="/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		User user = userService.findOne(id);
		
		if(user == null) {
			throw new UserNotFoundException("id-" + id);
		}
		
		//Bringing HATEOAS to send other things in the response object
		//"all-users",SERVER-PATH + "/users"
		//retrieveAll method url
		Resource<User> resource = new Resource<User>(user);
		ControllerLinkBuilder link = ControllerLinkBuilder.linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(link.withRel("all-users"));
		return resource;

	}
	
	@DeleteMapping(path="/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = userService.deleteById(id);
		
		if(user == null) {
			throw new UserNotFoundException("id-" + id);
		}
		
	}
	
	@PostMapping(path="/users")
	public ResponseEntity createUser(@Valid @RequestBody User user) {
		User savedUser =  userService.save(user);
		
		// First set the status to created means that code status will be 201
		//send back the url of the new created user /users/{id}
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(savedUser.getId()).toUri();
		
		//Created custom response body
		return ResponseEntity.created(location).build();
	}
}
