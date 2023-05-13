package com.rab3tech.customer.service;

import java.util.List;

import com.rab3tech.vo.CustomerProfilePicVO;

public interface CustomerProfilePicService {

	public void save(String username, byte[] photo,String description);

	List<Integer> findAllPicIds(String username);

	byte[] findPicById(int ppid);

	List<CustomerProfilePicVO> findAllPic(String username);

	void deletePicById(int ppid);

}
