package com.caionilson.core.tema8.model;

public class Book {

	private int bookId;
	private static int bookIds = 1;
	private String bookTitle;
	private String bookAuthor;
	private boolean bookLoan;

	public Book() {
	}

	public Book(int bookId, String bookTitle, String bookAuthor) {
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.bookAuthor = bookAuthor;
	}

	public Book(String bookTitle, String bookAuthor) {
		this.bookTitle = bookTitle;
		this.bookAuthor = bookAuthor;
		this.bookId = bookIds++;
		this.bookLoan = false;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public boolean isBookLoan() {
		return bookLoan;
	}

	public void setBookLoan(boolean bookLoan) {
		this.bookLoan = bookLoan;
	}

}
