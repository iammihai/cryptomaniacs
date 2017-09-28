package com.tradeshift.cryptomaniacs.boundary;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tradeshift.cryptomaniacs.controller.UserRepository;
import com.tradeshift.cryptomaniacs.entity.User;
import com.tradeshift.cryptomaniacs.entity.Wallet;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON)
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@RequestMapping(value = "{username}", method = RequestMethod.GET)
	public User get(@PathVariable("username") String username) {
		return userRepository.findByUsername(username);
	}

	@RequestMapping(method = RequestMethod.POST)
	public User save(@RequestBody User user) {
		return userRepository.save(user);
	}

	@RequestMapping(value = "/{username}/wallet", method = RequestMethod.GET)
	public List<Wallet> getWallets(@PathVariable("username") String username) {
		return get(username).getWallets();
	}

}
