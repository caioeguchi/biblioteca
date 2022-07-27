package com.caionilson.core.tema8;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import com.caionilson.core.tema8.dao.LoanDAO;
import com.caionilson.core.tema8.model.*;

import junit.framework.Assert;

public class LoanDAOTest {

	@Test
	public void isAddingLoan() {
		User user = new User(1, "name");
		Book book = new Book(1, "title", "author");
		LocalDate loanDate = LocalDate.now();
		LocalDate returnDate = LocalDate.now().plusDays(7);

		LoanDAO loanDAO = new LoanDAO();
		loanDAO.addLoan(user, book, loanDate, returnDate);

		Assert.assertTrue(loanDAO.getLoanList().contains(loanDAO.getLoanBookId(book.getBookId())));
	}

	@Test
	public void gettingLoandById() {
		User user = new User(1, "name");
		Book book = new Book(1, "title", "author");
		LocalDate loanDate = LocalDate.now();
		LocalDate returnDate = LocalDate.now().plusDays(7);

		LoanDAO loanDAO = new LoanDAO();
		loanDAO.addLoan(user, book, loanDate, returnDate);

		Assert.assertEquals(loanDAO.getLoanList().get(0), loanDAO.getLoanBookId(1));
	}

	@Test
	public void isListingAllLoans() {
		User user = new User(1, "name1");
		Book book = new Book(1, "title1", "author1");
		LocalDate loanDate = LocalDate.now();
		LocalDate returnDate = LocalDate.now().plusDays(7);

		User user2 = new User(2, "name2");
		Book book2 = new Book(2, "title2", "author2");
		LocalDate loanDate2 = LocalDate.now();
		LocalDate returnDate2 = LocalDate.now().plusDays(7);

		LoanDAO loanDao = new LoanDAO();
		loanDao.addLoan(user, book, loanDate, returnDate);
		loanDao.addLoan(user2, book2, loanDate2, returnDate2);

		List<Loan> loanList = loanDao.getLoanList();

		Assert.assertEquals(loanDao.getLoanList().get(0), loanList.get(0));
		Assert.assertEquals(loanDao.getLoanList().get(1), loanList.get(1));
	}

	@Test
	public void isRemovingBookLoan() {
		User user = new User(1, "name1");
		Book book = new Book(1, "title1", "author1");
		LocalDate loanDate = LocalDate.now();
		LocalDate returnDate = LocalDate.now().plusDays(7);

		LoanDAO loanDAO = new LoanDAO();
		loanDAO.addLoan(user, book, loanDate, returnDate);

		loanDAO.removeLoan(loanDAO.getLoanBookId(book.getBookId()));

		Assert.assertTrue(!loanDAO.getLoanList().contains(loanDAO.getLoanBookId(book.getBookId())));
	}
}
