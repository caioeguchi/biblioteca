package com.caionilson.core.tema8.model;

import java.time.LocalDate;

public class Loan {
	private User user;
	private Book book;
	private LocalDate loanDate, returnDate;

	public Loan(User user, Book book, LocalDate loanDate, LocalDate returnDate) {
		this.user = user;
		this.book = book;
		this.loanDate = loanDate;
		this.returnDate = returnDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LocalDate getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(LocalDate loanDate) {
		this.loanDate = loanDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

}
