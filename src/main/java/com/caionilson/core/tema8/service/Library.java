package com.caionilson.core.tema8.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.caionilson.core.tema8.Exception.LibraryException;
import com.caionilson.core.tema8.dao.*;
import com.caionilson.core.tema8.model.*;

public class Library {

	private BookDAO bookDAO;
	private UserDAO userDAO;
	private LoanDAO loanDAO;

	public Library() {
		this.bookDAO = new BookDAO();
		this.userDAO = new UserDAO();
		this.loanDAO = new LoanDAO();
	}

	public BookDAO getBookService() {
		return bookDAO;
	}

	public UserDAO getUserService() {
		return userDAO;
	}

	public LoanDAO getLoanService() {
		return loanDAO;
	}

	public void addBook(String bookTitle, String bookAuthor) {

		if (bookTitle.isEmpty())
			throw new LibraryException("Tituo do livro precisa ser preenchido");
		if (bookAuthor.isEmpty())
			throw new LibraryException("Autor do livro precisa ser preenchido");
		bookDAO.addBook(new Book(bookTitle, bookAuthor));
	}

	public List<Book> getBookList() {
		return bookDAO.getBookList();
	}

	public void removeBook(int bookId) {

		if (bookDAO.getBookId(bookId) == null) 
			throw new LibraryException("Livro não encontrado");

		bookDAO.removeBook(bookId);
		System.out.println("Livro excluído");
		
	}

	public List<Book> searchBook(String searchBook) {
		return bookDAO.getBookList().stream()
				.filter(l -> l.getBookTitle().toLowerCase().contains(searchBook.toLowerCase())
						|| l.getBookAuthor().toLowerCase().contains(searchBook.toLowerCase()))
				.collect(Collectors.toList());
	}

	public Book getBook(String titulo, String autor) {
		return bookDAO.getBook(titulo, autor);
	}

	public void addUser(String userName) {
		userDAO.addUser(new User(userName));
	}

	public List<User> getUserList() {
		return userDAO.getUserList();
	}

	public void loanAdd(int userId, int bookId) {
		Book book = bookDAO.getBookId(bookId);
		User user = userDAO.getUserId(userId);

		if (userDAO.getUserId(userId) == null)
			throw new LibraryException("Usuário não encontrado");
		if (bookDAO.getBookId(bookId) == null)
			throw new LibraryException("Livro não encontrado");
		if (book.isBookLoan())
			throw new LibraryException("Livro já está alugado");
		if (user.getUserLoan() > 4)
			throw new LibraryException("O usuário já tem 5 livros alugados");

		loanDAO.addLoan(user, book, LocalDate.now(), LocalDate.now().plusDays(7));
		user.setUserLoan(user.getUserLoan() + 1);
		user.setUserLoanTotal(user.getUserLoanTotal() + 1);
		book.setBookLoan(true);
		if (book.isBookLoan())
			System.out.println("\nLivro emprestado\n");

	}

	public void loanRenew(int userId, int bookId) {
		Book book = bookDAO.getBookId(bookId);

		if (userDAO.getUserId(userId) == null)
			throw new LibraryException("Usuário não encontrado");
		if (bookDAO.getBookId(bookId) == null)
			throw new LibraryException("Livro não encontrado");
		
		Loan loan = loanDAO.getLoanBookId(book.getBookId());
		Period difference = Period.between(loan.getReturnDate(), LocalDate.now());
		int daysOfDelay = 0;
		if (difference.getMonths() == 0 && difference.getYears() == 0) {
			daysOfDelay += difference.getDays();
		}
		if (difference.getMonths() > 0) {
			daysOfDelay += difference.getMonths() * 30;
		}
		if (difference.getYears() > 0) {
			daysOfDelay += difference.getYears() * 365;
		}
		//se o livro demorou mais de 7 dias para ser entregue, ele nao renova e manda a LibraryException
		if (((daysOfDelay < 0) ? daysOfDelay * -1 : daysOfDelay) > 7) {
			throw new LibraryException("Entrega atrasada, livro não pode ser renovado");
		} else {
			
		//se o livro atende os requisitos, ele adiciona 7 dias do dia previsto de entrega
			loan.setReturnDate(loan.getReturnDate().plusDays(7));
		}

	}
	

	public void loanReturn(int userId, int bookId) {
		int penaltyPricePerDay = 5;
		Book book = bookDAO.getBookId(bookId);
		User user = userDAO.getUserId(userId);

		if (userDAO.getUserId(userId) == null)
			throw new LibraryException("Usuário não encontrado");
		if (bookDAO.getBookId(bookId) == null)
			throw new LibraryException("Livro não encontrado");

		Loan loan = loanDAO.getLoanBookId(book.getBookId());
		if (Period.between(loan.getReturnDate(), LocalDate.now()).getDays() > 7) {
			user.setUserLoanChargeDays(Period.between(loan.getReturnDate(), LocalDate.now()).getDays() * penaltyPricePerDay);
		}
		loanDAO.removeLoan(loan);
		user.setUserLoan(user.getUserLoan() - 1);
		book.setBookLoan(false);
	}

	public List<Loan> loanList() {
		return loanDAO.getLoanList();
	}

	public List<User> top10Users() {
		List<User> listOfUsers = userDAO.getUserList();
		List<User> top10 = new ArrayList<>();

		listOfUsers.sort((User u1, User u2) -> u2.getUserLoanTotal() - u1.getUserLoanTotal());
		if (listOfUsers.size() < 10) {
			return listOfUsers;
		} else {
			for (int i = 0; i < 10; i++) {
				top10.add(listOfUsers.get(i));
			}
		}

		return top10;
	}

	public List<Loan> loanDelay() {
		List<Loan> delayed = new ArrayList<>();

		for (Loan loan : loanDAO.getLoanList()) {
			Period difference = Period.between(loan.getReturnDate(), LocalDate.now());
			int delay = 0;
			if (difference.getMonths() == 0 && difference.getYears() == 0) {
				delay += difference.getDays();
			}
			if (difference.getMonths() > 0) {
				delay += difference.getMonths() * 30;
			}
			if (difference.getYears() > 0) {
				delay += difference.getYears() * 365;
			}
			if (((delay < 0) ? delay * -1 : delay) > 7) {
				delayed.add(loan);
			}
		}
		return delayed;
	}
}
