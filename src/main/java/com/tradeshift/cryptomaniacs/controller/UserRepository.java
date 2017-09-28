package com.tradeshift.cryptomaniacs.controller;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradeshift.cryptomaniacs.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

}
