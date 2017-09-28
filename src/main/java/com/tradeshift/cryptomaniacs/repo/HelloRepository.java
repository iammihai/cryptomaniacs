package com.tradeshift.cryptomaniacs.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HelloRepository extends JpaRepository<Hello, Long> {

	List<Hello> findAllByOrderByIdDesc();

}
