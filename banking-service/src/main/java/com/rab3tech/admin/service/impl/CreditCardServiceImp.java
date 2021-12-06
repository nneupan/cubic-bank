package com.rab3tech.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.admin.dao.repository.CreditCardRepository;
import com.rab3tech.admin.service.CreditCardService;
import com.rab3tech.dao.entity.CreditCard;
import com.rab3tech.vo.CreditCardDTO;


@Service
@Transactional
public class CreditCardServiceImp implements CreditCardService{

	@Autowired
	private CreditCardRepository cardRepo;

	

	@Override
	public boolean save(CreditCardDTO card) {
		try {
			CreditCard c = new CreditCard();
			BeanUtils.copyProperties(card, c);
			cardRepo.save(c);
			return true;
		}
		catch(Exception e) {
			return false;
		}
		
		
	}

	@Override
	public List<CreditCardDTO> findAll() {
		List<CreditCardDTO> cardDTOs = new ArrayList<>();
		List<CreditCard> cards = cardRepo.findAll();
		for(CreditCard c : cards) {
			CreditCardDTO cdto = new CreditCardDTO();
			BeanUtils.copyProperties(c, cdto);
			cardDTOs.add(cdto);
		}
		return cardDTOs;
	}

	@Override
	public void deleteByName(String name) {
		cardRepo.deleteByName(name);
		
	}

}
