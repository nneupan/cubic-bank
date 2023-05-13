package com.rab3tech.utils;

import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.rab3tech.vo.CreditCardDTO;

@Service
public class CreditCardDTOTypeComparatorAsscending implements Comparator<CreditCardDTO>{

	@Override
	public int compare(CreditCardDTO arg0, CreditCardDTO arg1) {
		// TODO Auto-generated method stub
		return arg0.getType().compareTo(arg1.getType());
	}
	

}
