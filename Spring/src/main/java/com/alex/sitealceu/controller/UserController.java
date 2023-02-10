package com.alex.sitealceu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alex.sitealceu.model.ItemNivel3;
import com.alex.sitealceu.model.User;
import com.alex.sitealceu.repository.IUserRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")

public class UserController {

	@Autowired
	private IUserRepository userRepository;

	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<User> listar() {

		List<User> response = userRepository.findAll();

		response.forEach(profile -> {

			setMaturidadeNivel3(profile);

		});

		return response;

	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<User> buscar(@PathVariable("id") int param) {

		List<User> response = userRepository.findById(param).stream().toList();

		return response;

	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody User adicionar(@RequestBody User novoProfile) {
		return userRepository.save(novoProfile);
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public @ResponseBody User login(@RequestBody User credentials) {

		List<User> response = userRepository.findAll().stream().toList();

		for (User user : response) {
			if (credentials.getEmail().equals(user.getEmail())
					&& credentials.getPassword().equals(user.getPassword())) {
				return user;
			}
		}

		return null;
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Optional<User> atualizar(@PathVariable("id") int param,
			@RequestBody User profileAtualizado) {

		User atualizado = userRepository.findById(param).get();
		atualizado.setName(profileAtualizado.getName());
		atualizado.setLast_name(profileAtualizado.getLast_name());
		atualizado.setEmail(profileAtualizado.getEmail());
		atualizado.setPassword(profileAtualizado.getPassword());
		atualizado.setPost(profileAtualizado.getPost());
		atualizado.setImage(profileAtualizado.getImage());

		userRepository.save(atualizado);

		return userRepository.findById(param);
	}


	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public @ResponseBody boolean deletar(@PathVariable("id") int param) {
		userRepository.deleteById(param);
		return !userRepository.existsById(param);
	}

	private void setMaturidadeNivel3(User user) {

		final String PATH = "localhost:8080/profile";

		user.setLinks(new ArrayList<>());

		user.getLinks().add(new ItemNivel3("GET", PATH));
		user.getLinks().add(new ItemNivel3("GET", PATH + "/" + user.getId()));
		user.getLinks().add(new ItemNivel3("POST", PATH));
		user.getLinks().add(new ItemNivel3("PUT", PATH + "/" + user.getId()));

	}
}
