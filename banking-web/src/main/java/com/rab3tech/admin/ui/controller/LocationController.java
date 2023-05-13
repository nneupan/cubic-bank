package com.rab3tech.admin.ui.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rab3tech.admin.service.CustomerLocationService;
import com.rab3tech.service.exception.BankServiceException;
import com.rab3tech.utils.UserSessionUtils;
import com.rab3tech.vo.LocationVO;
import com.rab3tech.vo.LoginVO;



@Controller
@RequestMapping("/admin")
public class LocationController {
	 
	@Autowired
	private CustomerLocationService locationService;
	
	@GetMapping("/locations")
	public String showLocation(Model model) {
		List<LocationVO> locationVO = locationService.findLocation();
		model.addAttribute("locationVO", locationVO);
		return "admin/locations";		
	}
	
	@PostMapping("/locations")
	public String createLocation (@ModelAttribute LocationVO locationVO,HttpSession session, Model model)throws IOException{
		locationVO.setLogin(UserSessionUtils.findUserFromSession(session));
		locationService.save(locationVO);
		return "redirect:/admin/locations";
		
	}
}
