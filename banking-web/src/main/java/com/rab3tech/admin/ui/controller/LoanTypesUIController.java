package com.rab3tech.admin.ui.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rab3tech.admin.service.LoanTypesService;
import com.rab3tech.vo.LoanTypesVO;

@Controller
@RequestMapping("/admin")
@Scope("singleton")  //we have seven scopes
//@CrossOrigin()
public class LoanTypesUIController {
	
	
	@Autowired
	private LoanTypesService loanTypesService;
	
	
	@GetMapping("/loantypes")
	public String showuploadLoanTypes(Model model) {
		List<LoanTypesVO> loantypeVOs=loanTypesService.getAll();
		//model is used to send data from controller to view (thyme leaf page)
		model.addAttribute("loantypeVOs",loantypeVOs);
		//model is used to send data from controller to UI
		return "admin/loantypes"; 
	}
	
	//dloantype ? id =222
	@GetMapping("/dloantype")
	public String deleteLoanType(@RequestParam int id,Model model) {
		loanTypesService.deleteData(id);
		//below line will hit controller again to show the data
		return "redirect:/admin/loantypes"; 
	}
	
	
	@GetMapping("/loantype")
	public String showuploadLoanType(Model model) {
		LoanTypesVO loantypeVO=new LoanTypesVO();
		model.addAttribute("loantypeVO",loantypeVO);
		//model is used to send data from controller to UI
		return "admin/loantype"; 
	}
	
	
	@PostMapping("/loantype")
	public String uploadLoanType(@ModelAttribute LoanTypesVO loanTypesVO,Model model) {
		loanTypesVO.setDoe(new Timestamp(new Date().getTime()));
		LoanTypesVO loantypeVO=new LoanTypesVO();
		loanTypesService.save(loanTypesVO);
		model.addAttribute("loantypeVO",loantypeVO);
		model.addAttribute("message","Hi record is added successfully!!");
		return "admin/loantype"; 
	}
}
