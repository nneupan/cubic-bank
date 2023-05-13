package com.rab3tech.admin.service;

import java.util.List;

import com.rab3tech.vo.LoanTypesVO;

public interface LoanTypesService {

	void save(LoanTypesVO loanTypesVO);

	List<LoanTypesVO> getAll();

	LoanTypesVO findById(int did);

	void deleteData(int did);

	void update(LoanTypesVO loanTypesVO);

}
