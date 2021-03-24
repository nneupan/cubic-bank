package com.rab3tech.customer.ui.controller;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rab3tech.admin.service.CustomerAccountInfoService;
import com.rab3tech.admin.service.PayeeInfoService;
import com.rab3tech.customer.service.CustomerService;
import com.rab3tech.customer.service.LoginService;
import com.rab3tech.customer.service.impl.CustomerEnquiryService;
import com.rab3tech.customer.service.impl.SecurityQuestionService;
import com.rab3tech.email.service.EmailService;
import com.rab3tech.vo.AccountStatementVO;
import com.rab3tech.vo.ChangePasswordVO;
import com.rab3tech.vo.CustomerAccountInfoVO;
import com.rab3tech.vo.CustomerSavingVO;
import com.rab3tech.vo.CustomerSecurityQueAnsVO;
import com.rab3tech.vo.CustomerVO;
import com.rab3tech.vo.EmailVO;
import com.rab3tech.vo.LoginVO;
import com.rab3tech.vo.PayeeInfoVO;
import com.rab3tech.vo.SecurityQuestionsVO;
import com.rab3tech.vo.TransactionInfoVO;

/**
 * 
 * @author nagendra This class for customer GUI
 *
 */
@Controller
public class CustomerUIController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerUIController.class);

	@Autowired
	private CustomerEnquiryService customerEnquiryService;

	@Autowired
	private SecurityQuestionService securityQuestionService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private CustomerAccountInfoService customerAccountInfoService;

	@Autowired
	private PayeeInfoService payeeInfoService;

	@PostMapping("/customer/changePassword")
	public String saveCustomerQuestions(@ModelAttribute ChangePasswordVO changePasswordVO, Model model,
			HttpSession session) {
		LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO");
		String loginid = loginVO2.getUsername();
		changePasswordVO.setLoginid(loginid);
		String viewName = "customer/dashboard";
		boolean status = loginService.checkPasswordValid(loginid, changePasswordVO.getCurrentPassword());
		if (status) {
			if (changePasswordVO.getNewPassword().equals(changePasswordVO.getConfirmPassword())) {
				viewName = "customer/dashboard";
				loginService.changePassword(changePasswordVO);
			} else {
				model.addAttribute("error", "Sorry , your new password and confirm passwords are not same!");
				return "customer/login"; // login.html
			}
		} else {
			model.addAttribute("error", "Sorry , your username and password are not valid!");
			return "customer/login"; // login.html
		}
		return viewName;
	}

	@PostMapping("/customer/securityQuestion")
	public String saveCustomerQuestions(
			@ModelAttribute("customerSecurityQueAnsVO") CustomerSecurityQueAnsVO customerSecurityQueAnsVO, Model model,
			HttpSession session) {
		LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO");
		String loginid = loginVO2.getUsername();
		customerSecurityQueAnsVO.setLoginid(loginid);
		securityQuestionService.save(customerSecurityQueAnsVO);
		//
		return "customer/chagePassword";
	}

	// http://localhost:444/customer/account/registration?cuid=1585a34b5277-dab2-475a-b7b4-042e032e8121603186515
	@GetMapping("/customer/account/registration")
	public String showCustomerRegistrationPage(@RequestParam String cuid, Model model) {

		logger.debug("cuid = " + cuid);
		Optional<CustomerSavingVO> optional = customerEnquiryService.findCustomerEnquiryByUuid(cuid);
		CustomerVO customerVO = new CustomerVO();

		if (!optional.isPresent()) {
			return "customer/error";
		} else {
			// model is used to carry data from controller to the view =- JSP/
			CustomerSavingVO customerSavingVO = optional.get();
			customerVO.setEmail(customerSavingVO.getEmail());
			customerVO.setName(customerSavingVO.getName());
			customerVO.setMobile(customerSavingVO.getMobile());
			customerVO.setAddress(customerSavingVO.getLocation());
			customerVO.setToken(cuid);
			logger.debug(customerSavingVO.toString());
			// model - is hash map which is used to carry data from controller to thyme
			// leaf!!!!!
			// model is similar to request scope in jsp and servlet
			model.addAttribute("customerVO", customerVO);
			return "customer/customerRegistration"; // thyme leaf
		}
	}

	@PostMapping("/customer/account/registration")
	public String createCustomer(@ModelAttribute CustomerVO customerVO, Model model) {
		// saving customer into database
		logger.debug(customerVO.toString());
		customerVO = customerService.createAccount(customerVO);
		
		
		
		// Write code to send email
		EmailVO mail = new EmailVO(customerVO.getEmail(), "javahunk2020@gmail.com",
				"Regarding Customer " + customerVO.getName() + "  userid and password", "", customerVO.getName());
		
		
		// ^^ need this explained a little to understand what's happening and
		// where the info is going 
		mail.setUsername(customerVO.getUserid());
		mail.setPassword(customerVO.getPassword());
		emailService.sendUsernamePasswordEmail(mail);
		System.out.println(customerVO); // why this when we used logger earlier? 
										// is it because we want to see it in the console? (developer)
		model.addAttribute("loginVO", new LoginVO());
		model.addAttribute("message", "Your account has been setup successfully , please check your email.");
		return "customer/login";
	}

	@GetMapping(value = { "/customer/account/enquiry", "/", "/mocha", "/welcome" })
	public String showCustomerEnquiryPage(Model model) {
		CustomerSavingVO customerSavingVO = new CustomerSavingVO();
		// model is map which is used to carry object from controller to view
		model.addAttribute("customerSavingVO", customerSavingVO);
		return "customer/customerEnquiry"; // customerEnquiry.html
	}

	@PostMapping("/customer/account/enquiry")
	public String submitEnquiryData(@ModelAttribute CustomerSavingVO customerSavingVO, Model model) {
		boolean status = customerEnquiryService.emailNotExist(customerSavingVO.getEmail());
		logger.info("Executing submitEnquiryData");
		if (status) {
			CustomerSavingVO response = customerEnquiryService.save(customerSavingVO);
			logger.debug("Hey Customer , your enquiry form has been submitted successfully!!! and appref "
					+ response.getAppref());
			model.addAttribute("message",
					"Hey Customer , your enquiry form has been submitted successfully!!! and appref "
							+ response.getAppref());
		} else {
			model.addAttribute("message", "Sorry , this email is already in use " + customerSavingVO.getEmail());
		}
		return "customer/success"; // customerEnquiry.html

	}

	@GetMapping("/customer/myProfile")
	public String myProfilePage(Model model, HttpSession session) {

//		HttpSession session = request.getSession(); 
		LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO"); // checks if user is logged in or not - this
																			// is the confirmation

		if (loginVO2 != null) {

			String loginid = loginVO2.getUsername();
			CustomerVO customer = customerService.getCustomer(loginid); // loginid is also email in this situation
			CustomerSecurityQueAnsVO quesAns = securityQuestionService.findQuestionAnswer(loginid);
			customer.setQuestion1(quesAns.getSecurityQuestion1());
			customer.setQuestion2(quesAns.getSecurityQuestion2());
			customer.setAnswer1(quesAns.getSecurityQuestionAnswer1());
			customer.setAnswer2(quesAns.getSecurityQuestionAnswer2());

			model.addAttribute("customerVO", customer);
			List<SecurityQuestionsVO> securityQuestionsVO = securityQuestionService.findAll();

			System.out.println("hello this is where the questions should be " + securityQuestionsVO.size());

			model.addAttribute("securityQuestionsVO", securityQuestionsVO);

			return "customer/myProfile";

		} else {

			return "customer/login";

		}

//		Optional<CustomerSavingVO> optional = customerEnquiryService.findCustomerEnquiryByUuid(cuid);
//		model.addAttribute("CustomerSavingVO", optional);

	}

	@PostMapping("/customer/account/updateCustomer")
	public String updateCustomer(Model model, @ModelAttribute CustomerVO customerVO, HttpSession session,
			@RequestParam(value = "error", required = false) boolean messsage) {

		LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO");

		if (loginVO2 != null) {

			CustomerSecurityQueAnsVO customerSecurityQueAnsVO = new CustomerSecurityQueAnsVO();
			customerSecurityQueAnsVO.setLoginid(loginVO2.getUsername());
			customerSecurityQueAnsVO.setSecurityQuestion1(customerVO.getQuestion1());
			customerSecurityQueAnsVO.setSecurityQuestion2(customerVO.getQuestion2());
			customerSecurityQueAnsVO.setSecurityQuestionAnswer1(customerVO.getAnswer1());
			customerSecurityQueAnsVO.setSecurityQuestionAnswer2(customerVO.getAnswer2());
			customerService.updateCustomer(customerVO);

			securityQuestionService.update(customerSecurityQueAnsVO);
			model.addAttribute("message", "User has been updated successfully");
			model.addAttribute("customerVO", customerVO); // passing the updated 192

			List<SecurityQuestionsVO> securityQuestionsVO = securityQuestionService.findAll();

			System.out.println("hello this is where the questions should be " + securityQuestionsVO.size());

			model.addAttribute("securityQuestionsVO", securityQuestionsVO);

			return "customer/myProfile";
		} else {

			return "customer/login";

		}

	}

	@GetMapping("/customer/addPayee")
	public String addPayee(Model model, HttpSession session) {

		LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO");

		if (loginVO2 != null) {
			CustomerAccountInfoVO customerAccountInfoVO = customerAccountInfoService.findCustomer(loginVO2.getUsername());

			if (customerAccountInfoVO == null) {
				model.addAttribute("message", "yO");

			}

			return "customer/addPayee";
		} else {
			return "customer/login";

		}
	}

	@PostMapping("/customer/savePayee")

	public String savePayee(@ModelAttribute PayeeInfoVO payeeInfo, Model model, HttpSession session) {
		LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO");
		if (loginVO2 != null) {

			payeeInfo.setCustomerId(loginVO2.getUsername());
			String message = payeeInfoService.savePayee(payeeInfo);
			model.addAttribute("message", message);
			return "customer/addPayee";

		} else {
			return "customer/login";
		}
	}
	
	@GetMapping("/customer/transaction") 
	public String transaction (Model model, HttpSession session ) {
		LoginVO loginVO2=(LoginVO)session.getAttribute("userSessionVO");
		
		if (loginVO2 !=null) {			
			List <PayeeInfoVO> payeeInfoVOs = payeeInfoService.findByCustomerid(loginVO2.getUsername());
		model.addAttribute("payeeInfoVOs", payeeInfoVOs);

		return "customer/transaction"; 
		} else {
		return "customer/login";
		
}
	}
		
		@PostMapping("/customer/saveTransaction")

		public String savePayee (@ModelAttribute TransactionInfoVO transactionInfoVO, Model model, HttpSession session) {
			LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO");
			if (loginVO2 != null) {

				transactionInfoVO.setCustomerId(loginVO2.getUsername());
				String message = customerAccountInfoService.saveTransaction(transactionInfoVO);
				model.addAttribute("message", message);
				List <PayeeInfoVO> payeeInfoVOs = payeeInfoService.findByCustomerid(loginVO2.getUsername());
				model.addAttribute("payeeInfoVOs", payeeInfoVOs);
				return "customer/transaction";

			} else {
				return "customer/login";
			}
	}
		@GetMapping("/customer/accountStatement") 
		public String accountStatement (Model model, HttpSession session ) {
			LoginVO loginVO2=(LoginVO)session.getAttribute("userSessionVO");
			
			if (loginVO2 !=null) {			
				List<AccountStatementVO>  statements = customerAccountInfoService.getStatement(loginVO2.getUsername());
			System.out.println("statement ==== "+statements);
			model.addAttribute("AccountStatementVO", statements);


			return "customer/accountStatement"; 
			} else {
			return "customer/login";
}
		}
		
		@GetMapping("/customer/managePayee")
		public String managePayee(Model model, HttpSession session) {
			LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO");
			if (loginVO2 != null) {
				// check if user has account or not
				List<PayeeInfoVO> payeeInfoVO = payeeInfoService.findByCustomerid(loginVO2.getUsername());
					model.addAttribute("payeeInfoVO", payeeInfoVO);
				return "/customer/managePayee";
			}else
			return "customer/login";
		}
		
		@PostMapping("/customer/editPayeeInfo")
		public String editPayee (@ModelAttribute PayeeInfoVO payeeInfoVO, Model model, HttpSession session) {
			LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO");
			if (loginVO2 != null) {		
				payeeInfoService.editPayee(payeeInfoVO);
				List <PayeeInfoVO> payeeInfoVOs = payeeInfoService.findByCustomerid(loginVO2.getUsername());
				model.addAttribute("payeeInfoVOs", payeeInfoVOs);
				model.addAttribute("message", "Payee has been updated succesfully");
				return "/customer/managePayee";

			} else {
				return "customer/login";
			}
		}

}
