package com.rab3tech.admin.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rab3tech.admin.service.CreditCardService;
import com.rab3tech.utils.CreditCardDTONameComparatorAsscending;
import com.rab3tech.utils.CreditCardDTONameComparatorDecending;
import com.rab3tech.utils.CreditCardDTOTypeComparatorAsscending;
import com.rab3tech.utils.CreditCardDTOTypeComparatorDecending;
import com.rab3tech.vo.CreditCardDTO;


// th:href="@{/admin/addBranch}
@Controller
@RequestMapping("/admin/creditcards")
public class CreditCardContorller {
	
	@Autowired
	private CreditCardService cardService;
	
	@Autowired
	private CreditCardDTONameComparatorAsscending cardNameComparatorAssecending;
	@Autowired
	private CreditCardDTONameComparatorDecending cardNameComparatorDecending;
	
	@Autowired
	private CreditCardDTOTypeComparatorDecending cardTypeComparatorDecending;
	
	@Autowired
	private CreditCardDTOTypeComparatorAsscending cardTypeComparatorAsscending;
	
	@GetMapping("/addCard")
	public String addBranch(Model model){
		CreditCardDTO cardDTO=new CreditCardDTO();
		model.addAttribute("cardDTO", cardDTO);
		return "admin/addCard";
	}
	
	//@ModelAttribute ->> map all the coming from form data into Java object
	@PostMapping("/addCard")
	public String addCardPost(@ModelAttribute CreditCardDTO cardDTO){

		cardService.save(cardDTO);
		return "redirect:/admin/creditcards/showCards";
	}
	
	//@ModelAttribute ->> map all the coming from form data into Java object
		@GetMapping("/showCards")
		public String showCards(Model model,HttpServletRequest request){
			List<CreditCardDTO> cards = (ArrayList<CreditCardDTO>)cardService.findAll();
			
			if( "sortByNameAsscending".equals(request.getParameter("sort"))) {
				Collections.sort(cards,cardNameComparatorAssecending);
				
			}
			else if("sortByNameDecending".equals(request.getParameter("sort"))) {
				Collections.sort(cards,cardNameComparatorDecending);
				
			}
			else if("sortByTypeAsscending".equals(request.getParameter("sort"))) {
				Collections.sort(cards,cardTypeComparatorAsscending);
			}
			else if("sortByTypeDecending".equals(request.getParameter("sort"))) {
				Collections.sort(cards,cardTypeComparatorDecending);
			}
			
				model.addAttribute("cardDTOs",cards);
			
		
			
			
		
		return "/admin/cards";
			
		}
		
		
		@GetMapping("/deleteCard")
		public String deleteCard(@RequestParam("name") String name){
		
			cardService.deleteByName(name);
		
			return "redirect:/admin/creditcards/showCards";
		}
	
	
	
}
