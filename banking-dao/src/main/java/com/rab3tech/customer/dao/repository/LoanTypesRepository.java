package com.rab3tech.customer.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rab3tech.dao.entity.LoanTypes;

/**
 * 
 * @author javahunk
 * 
 * My dao layer is ready!
 * HQL
 *
 */
public interface LoanTypesRepository extends JpaRepository<LoanTypes, Integer> {

	public Optional<LoanTypes> findByLcode(String code);
	public Optional<LoanTypes> findByName(String name);
	public Optional<LoanTypes> findByDescription(String description);
}
