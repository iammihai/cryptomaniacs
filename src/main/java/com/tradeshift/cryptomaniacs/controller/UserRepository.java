package com.tradeshift.cryptomaniacs.controller;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradeshift.cryptomaniacs.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	User findByUsername(String username);

}
