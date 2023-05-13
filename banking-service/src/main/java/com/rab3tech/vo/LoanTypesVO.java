package com.rab3tech.vo;

import java.sql.Timestamp;

//CTR+A -><>> CTRL+SHIFT+F
public class LoanTypesVO {

	private int id;
	private String lcode;
	private String name;
	private String description;
	private Timestamp doe;

	public LoanTypesVO() {

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
