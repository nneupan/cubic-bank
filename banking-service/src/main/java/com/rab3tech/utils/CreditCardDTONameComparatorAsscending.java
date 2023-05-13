package com.rab3tech.utils;

import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.rab3tech.vo.CreditCardDTO;

@Service
public class CreditCardDTONameComparatorAsscending implements Comparator<CreditCardDTO>{

	@Override
	public int compare(CreditCardDTO arg0, CreditCardDTO arg1) {
		
		return arg0.getName().toLowerCase().compareTo(arg1.getName().toLowerCase());
	}
	

}
