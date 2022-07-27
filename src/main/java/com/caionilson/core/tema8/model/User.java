package com.caionilson.core.tema8.model;

public class User {

	private int userId, userLoan, userLoanTotal, userLoanChargeDays;
	private static int userIds = 0;
	private String userName;

	public User () {}
	
	
	public User(int userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}


	public User(String userName) {
		this.userName = userName;
		this.userId = userIds++;
	}

	public User(String userName, int userLoan, int userLoanTotal) {
		this.userId = userIds++;
		this.userName = userName;
		this.userLoan = userLoan;
		this.userLoanTotal = userLoanTotal;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserLoan() {
		return userLoan;
	}

	public void setUserLoan(int userLoan) {
		this.userLoan = userLoan;
	}

	public int getUserLoanTotal() {
		return userLoanTotal;
	}

	public void setUserLoanTotal(int userLoanTotal) {
		this.userLoanTotal = userLoanTotal;
	}

	public int getUserLoanChargeDays() {
		return userLoanChargeDays;
	}

	public void setUserLoanChargeDays(int userLoanChargeDays) {
		this.userLoanChargeDays = userLoanChargeDays;
	}

}
