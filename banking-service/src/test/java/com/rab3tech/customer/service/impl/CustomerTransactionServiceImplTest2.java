
package com.rab3tech.customer.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.customer.dao.repository.CustomerTransactionRepository;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.CustomerTransaction;
import com.rab3tech.dao.entity.Login;
import com.rab3tech.vo.CustomerTransactionVO;

@RunWith(MockitoJUnitRunner.class)
public class CustomerTransactionServiceImplTest2 {
	
	@Mock
	private CustomerTransactionRepository customerTransactionRepository;
	
	@Mock
	private CustomerAccountInfoRepository customerAccountInfoRepository;
	
	//Here this is creating real instance of CustomerTransactionServiceImpl
	//and injecting it;s dependency by mock instance created in above
	//lines
	@InjectMocks
	private CustomerTransactionServiceImpl customerTransactionService;
	
	@Before
	public void chehchchc() {
		//this keyword always refer current class object 
		MockitoAnnotations.initMocks(this); //Initializing mocking for each test cases
	}
	
	
	@Test(expected = RuntimeException.class)
	public void findCustomerTransactionWhenAccountNotExist() {
		CustomerAccountInfo customerAccountInfo=null;
		
		//Optional<CustomerAccountInfo>
		//Stubing the behaviors
		when(customerAccountInfoRepository.findByLoginId("yadna01")).
		thenReturn(Optional.ofNullable(customerAccountInfo));
		//Here we are passing yadna01
		customerTransactionService.findCustomerTransaction("yadna01");
	}
	
	@Test
	public void findCustomerTransactionWhenAccountExist() {
	    
		String username="yadna01";
		CustomerAccountInfo customerAccountInfo=new CustomerAccountInfo();
		customerAccountInfo.setAccountNumber("9383726262");
		customerAccountInfo.setAccountType(null);
		customerAccountInfo.setAvBalance(122);
		customerAccountInfo.setBranch("SBA");
		customerAccountInfo.setCurrency("$");
		customerAccountInfo.setId(100);
		Login ologin=new Login();
		ologin.setLoginid(username);
		customerAccountInfo.setCustomerId(ologin);	
		
		//Making object Optional
		Optional<CustomerAccountInfo> ocustomerAccountInfo=Optional.of(customerAccountInfo);
		when(customerAccountInfoRepository.findByLoginId(username)).thenReturn(ocustomerAccountInfo);
		
		//Second we have to mock 
		List<CustomerTransaction> customerTransactions=new ArrayList<>();
		
		CustomerTransaction customerTransaction=new CustomerTransaction();
		customerTransaction.setAmount(122);
		customerTransaction.setBankName("Aba");
		customerTransaction.setFromAccount("9383726262");
		customerTransaction.setTransactionId("Tx828272");
		customerTransactions.add(customerTransaction);
		
		CustomerTransaction customerTransaction2=new CustomerTransaction();
		customerTransaction2.setAmount(34);
		customerTransaction2.setBankName("Magic Tech");
		customerTransaction2.setFromAccount("9383726262");
		customerTransaction2.setTransactionId("tx723636633");
		customerTransactions.add(customerTransaction2);
		
		when(customerTransactionRepository.
				findByFromAccount("9383726262")).thenReturn(customerTransactions);
	
		List<CustomerTransactionVO> customerTransactionVOs=customerTransactionService.findCustomerTransaction(username);
		assertNotNull(customerTransactionVOs);
		assertEquals(2, customerTransactionVOs.size());
		assertEquals("Aba", customerTransactionVOs.get(0).getBankName());
		assertEquals("Magic Tech", customerTransactionVOs.get(1).getBankName());
		verify(customerTransactionRepository, times(1)).findByFromAccount("9383726262");
		verify(customerAccountInfoRepository, times(1)).findByLoginId(username);
	}
	

}
