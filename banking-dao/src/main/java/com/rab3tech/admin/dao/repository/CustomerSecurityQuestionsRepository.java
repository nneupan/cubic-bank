package com.rab3tech.admin.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rab3tech.dao.entity.SecurityQuestions;

/**
 * 
 * @author nagendra
 *
 */
public interface CustomerSecurityQuestionsRepository extends JpaRepository<SecurityQuestions, Integer> {
	
	@Query ("Select c from CustomerQuestionAnswer c where c.login.loginid =:ploginid")
	List <SecurityQuestions> findQuestionAnswer(@Param ("ploginid") String ploginid);

}

