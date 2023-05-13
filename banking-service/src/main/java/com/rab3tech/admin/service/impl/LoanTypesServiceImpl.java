package com.rab3tech.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.admin.service.LoanTypesService;
import com.rab3tech.customer.dao.repository.LoanTypesRepository;
import com.rab3tech.dao.entity.LoanTypes;
import com.rab3tech.vo.LoanTypesVO;


//Who is creating bean of it ?????? spring application context or spring container


//Who will call service layer - either API or web project
@Service
public class LoanTypesServiceImpl implements LoanTypesService {
	
	@Autowired
	private LoanTypesRepository loanTypesRepository;
	
	/**
	 * LoanTypesVO data will come from outside of this application	
	 */
	@Override
	public void save(LoanTypesVO loanTypesVO) {
		LoanTypes loanTypes = new LoanTypes();
		BeanUtils.copyProperties(loanTypesVO, loanTypes);
		loanTypesRepository.save(loanTypes);
	}
	
	@Override
	@Transactional
	public void update(LoanTypesVO loanTypesVO) {
		//Loading entity class inside persistence content
		LoanTypes loanTypes=loanTypesRepository.findById(loanTypesVO.getId()).get();
		
		//Then change the state of it to update inside the database
		loanTypes.setDescription(loanTypesVO.getDescription());
		loanTypes.setLcode(loanTypesVO.getLcode());
		loanTypes.setName(loanTypesVO.getName());
	}
	
	@Override
	public void deleteData(int did) {
		loanTypesRepository.deleteById(did);
	}
	
	@Override
	public LoanTypesVO findById(int did) {
		LoanTypes loanTypes=loanTypesRepository.findById(did).get();
		LoanTypesVO loanTypesVO=new LoanTypesVO();
		BeanUtils.copyProperties(loanTypes, loanTypesVO);
		return loanTypesVO;
	}
	
	public List<LoanTypesVO> getAll() {
		List<LoanTypes> loanTypes= loanTypesRepository.findAll(); //List<LoanTypes>
		
		List<LoanTypesVO> loanTypesVOs=new ArrayList<>();
		
		for(LoanTypes loanType:loanTypes) {
			LoanTypesVO loanTypesVO=new LoanTypesVO();
			BeanUtils.copyProperties(loanType, loanTypesVO);
			loanTypesVOs.add(loanTypesVO);
		}
		return loanTypesVOs;
	}
	
	/*@Override
	public List<LoanTypesVO> getAll() {
		return loanTypesRepository.findAll() //List<LoanTypes>
				.stream() //Stream<LoanTypes>
				.map(loan-> { 
			LoanTypesVO loadTypesVO= new LoanTypesVO();
			BeanUtils.copyProperties(loan, loadTypesVO);
			return loadTypesVO; ////Stream<LoanTypesVO>
		}).collect(Collectors.toList());//List<LoanTypesVO>
	}*/

}
