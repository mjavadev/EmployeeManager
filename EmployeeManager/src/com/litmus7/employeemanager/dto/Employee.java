package com.litmus7.employeemanager.dto;

import java.time.LocalDate;

public class Employee {
	
	private int id;
	private String firstName;
	private String lastName;
	private String mobileNo;
	private String email;
	private LocalDate date;
	private boolean activeStatus;
	
	public Employee(int id, String firstName, String lastName, String mobileNo, String email, LocalDate date, boolean activeStatus) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNo = mobileNo;
		this.email = email;
		this.date = date;
		this.activeStatus = activeStatus;
	}
	
	public int getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getMobileNumber() {
		return mobileNo;
	}
	public String getEmail() {
		return email;
	}
	public LocalDate getDate() {
		return date;
	}
	public boolean getActiveStatus() {
		return activeStatus;
	}

}