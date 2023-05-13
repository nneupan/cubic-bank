package com.rab3tech.dao.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "loan_types_tbl")
public class LoanTypes{
	
	private int id;
	private String lcode;
	private String name;
	private String description;
	private Timestamp doe;
	
	public LoanTypes() {
		
	}


	public String getLcode() {
		return lcode;
	}


	public void setLcode(String lcode) {
		this.lcode = lcode;
	}



	public Timestamp getDoe() {
		return doe;
	}




	public void setDoe(Timestamp doe) {
		this.doe = doe;
	}




	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	@Column(length=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@Column(length=200)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
