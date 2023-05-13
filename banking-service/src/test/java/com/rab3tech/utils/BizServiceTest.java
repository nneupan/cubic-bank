package com.rab3tech.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BizServiceTest {
	
	@Mock
	private BizRepository bizRepo;
	
	@InjectMocks
	private BizService bizService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFooWhenInput4() {
		when(bizRepo.findById(5))
		.thenReturn(10);
		int result=bizService.foo(3);
		assertEquals(18, result);
	}
	
	@Test
	public void testFooWhenInput5() {
		when(bizRepo.findById(6))
		.thenReturn(77);
		int result=bizService.foo(4);
		assertEquals(85, result);
	}

}
