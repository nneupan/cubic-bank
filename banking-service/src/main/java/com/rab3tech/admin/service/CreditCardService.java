package com.rab3tech.admin.service;

import java.util.List;


import com.rab3tech.vo.CreditCardDTO;

public interface CreditCardService {
	boolean save(CreditCardDTO card);
	

	void deleteByName(String name);
	List<CreditCardDTO> findAll();
}
