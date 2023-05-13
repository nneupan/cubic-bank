package com.rab3tech.utils;

import javax.servlet.http.HttpSession;

import com.rab3tech.service.exception.BankServiceException;
import com.rab3tech.vo.LoginVO;

public class UserSessionUtils {
	
	public static LoginVO findUserFromSession(HttpSession session){
		LoginVO  loginVO=(LoginVO)session.getAttribute("userSessionVO");
		if(loginVO==null || loginVO.getUsername()==null) {
			BankServiceException bankServiceException=new BankServiceException("Sorry , Session is expired!!");
			throw bankServiceException;
		}
		return loginVO;
	}

}
