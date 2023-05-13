package com.rab3tech.mapper;

import java.util.Set;

import com.rab3tech.dao.entity.Customer;
import com.rab3tech.dao.entity.Role;
import com.rab3tech.vo.CustomerVO;

public class CustomerMapper {
	
	public static CustomerVO toVO(Customer customer) {
		
		CustomerVO customerVO=new CustomerVO();
		
		Set<Role> roles=customer.getLogin().getRoles();
		//Here we have only one record
		String roleName=null;
		 for(Role rr:roles) {
			 roleName=rr.getName();
			 break;
		 }
		customerVO.setRole(roleName);
		
		customerVO.setUserid(customer.getLogin().getLoginid());
		customerVO.setLocked(customer.getLogin().getLocked());
		customerVO.setAddress(customer.getAddress());
		customerVO.setEmail(customer.getEmail());
		customerVO.setGender(customer.getGender());
		customerVO.setMobile(customer.getMobile());
		customerVO.setName(customer.getName());
		customerVO.setSsn(customer.getSsn());
		customerVO.setId(customer.getId());
		return customerVO;
	}

}
