package com.rab3tech.admin.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.rab3tech.dao.entity.CreditCard;


public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
	
	@Modifying 	
	public void deleteByName(String name);
	
	public Optional<CreditCard> findByCode(String code);
	public Optional<CreditCard> findByName(String name);
}
