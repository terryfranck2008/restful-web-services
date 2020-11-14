package com.francktepi.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.francktepi.restfulwebservices.post.Post;
import com.francktepi.restfulwebservices.post.PostRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserJpaResources {

	@Autowired
	private UserDaoService userService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	@GetMapping(path = "/jpa/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping(path = "/jpa/users/{id}/posts")
	public List<Post> retrieveAllPosts(@PathVariable Integer id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}

		List<Post> posts = user.get().getPosts();

		return posts;
	}

	@GetMapping(path = "/jpa/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}

		// Bringing HATEOAS to send other things in the response object
		// "all-users",SERVER-PATH + "/users"
		// retrieveAll method url
		Resource<User> resource = new Resource<User>(user.get());
		ControllerLinkBuilder link = ControllerLinkBuilder.linkTo(methodOn(this.getClass()).retrieveAllUsers());

		resource.add(link.withRel("all-users"));
		return resource;

	}

	@DeleteMapping(path = "/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);

	}

	@PostMapping(path = "/jpa/users")
	public ResponseEntity createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);

		// First set the status to created means that code status will be 201
		// send back the url of the new created user /users/{id}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		// Created custom response body
		return ResponseEntity.created(location).build();
	}

	@PostMapping(path = "/jpa/users/{id}/posts")
	public ResponseEntity createPost(@PathVariable Integer id, @RequestBody Post post) {
		Optional<User> optionalUser = userRepository.findById(id);

		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		
		User user = optionalUser.get();
		post.setUser(user);
		
		postRepository.save(post);
		
		// First set the status to created means that code status will be 201
		// send back the url of the new created user /users/{id}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId())
				.toUri();

		// Created custom response body
		return ResponseEntity.created(location).build();
	}
}
