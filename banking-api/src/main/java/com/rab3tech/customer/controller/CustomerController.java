package com.rab3tech.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rab3tech.admin.service.PayeeInfoService;
import com.rab3tech.email.service.EmailService;
import com.rab3tech.vo.ApplicationResponseVO;
import com.rab3tech.vo.CustomerSavingVO;
import com.rab3tech.vo.CustomerVO;
import com.rab3tech.vo.EmailVO;
import com.rab3tech.vo.PayeeInfoVO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v3")
public class CustomerController {
	
	@Autowired
	private PayeeInfoService payeeInfoService;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/customer/findPayee")
	public List<PayeeInfoVO> findAllCustomers() {
		List<PayeeInfoVO>  responses=payeeInfoService.findAll();
		return responses;
	}

	@GetMapping("/customer/findPayee/{payeeId}")
	public PayeeInfoVO findPayee(@PathVariable int payeeId) {
	PayeeInfoVO payeeVO = new PayeeInfoVO();
	payeeVO = payeeInfoService.findPayeeById(payeeId);
	
	return payeeVO;
	}
	
	
	
	@GetMapping("/customer/deletePayee/{payeeId}")
	public ApplicationResponseVO deletePayee(@PathVariable int payeeId) {
		ApplicationResponseVO payeeVO = new ApplicationResponseVO();
	payeeInfoService.deletePayeeById(payeeId);
	payeeVO.setCode(200);
	payeeVO.setMessage("Payee has been deleted successfully!");
	payeeVO.setStatus("Success");
	return payeeVO;
	
	}
	
	@PostMapping("/customer/editPayee")
	public ApplicationResponseVO editPayee(@RequestBody PayeeInfoVO payeeInfoVO) {
		ApplicationResponseVO payeeVO = new ApplicationResponseVO();
	payeeInfoService.editPayee(payeeInfoVO);
	payeeVO.setCode(200);
	payeeVO.setMessage("Payee has been modified successfully!");
	payeeVO.setStatus("Success");
	
	PayeeInfoVO payee = findPayee(payeeInfoVO.getId());
	EmailVO email = new EmailVO(payee.getCustomerId(), "dfwsodas@gmail.com", "Edit Payee Info" , "", payee.getPayeeName());
	emailService.sendEditPayeeEmail(email);
	
	return payeeVO;
	
	}
	
	
}
