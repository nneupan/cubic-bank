package com.rab3tech.utils;

public class BizService {
	
	private BizRepository bizRepo=new BizRepository();
	
	public int foo(int x) {
		x=x+2;
		int result=bizRepo.findById(x);
		return result+8;
	}

}
