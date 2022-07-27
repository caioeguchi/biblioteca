package com.caionilson.core.tema8;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import com.caionilson.core.tema8.dao.*;
import com.caionilson.core.tema8.model.*;
import com.caionilson.core.tema8.service.Library;

import junit.framework.Assert;

public class LibraryTest {

	private Library library = new Library();
	private Book book = new Book();
	private BookDAO bookDAO = new BookDAO();

	@Test
	public void addBookIsWorking() {
		String title = "book";
		String author = "author";
		Book book = new Book(title, author);
		library.addBook(title, author);

		Assert.assertEquals("book", book.getBookTitle());
		Assert.assertEquals("author", book.getBookAuthor());
	}

	@Test
	public void estaExceluindoOsLivros() {
		int bookId = 0;
		bookDAO.addBook(book);

		bookDAO.removeBook(bookId);

		Assert.assertTrue(!bookDAO.getBookList().contains(bookDAO.getBookId(bookId)));
	}

	@Test
	public void buscaLivroPorTituloOuAutor() {
		library.addBook("book1", "author1");
		library.addBook("book2", "author2");
		library.addBook("book3", "author3");
		Book book3 = library.getBook("book3", "author3");
		List<Book> resultados = library.searchBook("book3");
		Assert.assertEquals(1, resultados.size());
		Assert.assertTrue(resultados.contains(book3));

	}

	@Test
	public void checaSeOClienteEstaSendoRegistrado() {
		String name = "name";
		User user = new User(name);
		library.addUser(name);

		Assert.assertEquals("name", user.getUserName());
	}

	@Test
	public void checaSeOsLivrosEstaoSendoRenovados() {

		User user = new User("name");
		Book book = new Book("title", "author");
		LocalDate loanDate = LocalDate.now();
		LocalDate returnDate = LocalDate.now().plusDays(7);

		LoanDAO loanDAO = new LoanDAO();
		loanDAO.addLoan(user, book, loanDate, returnDate);

		Assert.assertTrue(loanDAO.getLoanList().contains(loanDAO.getLoanBookId(book.getBookId())));
	}

	@Test
	public void checaSeOsLivrosEstaoSendoDevolvidos() {
		User user = new User("name");
		Book book = new Book("title", "author");
		LocalDate loanDate = LocalDate.now();
		LocalDate returnDate = LocalDate.now().plusDays(7);

		LoanDAO loanDAO = new LoanDAO();
		loanDAO.addLoan(user, book, loanDate, returnDate);

		loanDAO.removeLoan(loanDAO.getLoanBookId(book.getBookId()));
		Assert.assertTrue(!loanDAO.getLoanList().contains(loanDAO.getLoanBookId(book.getBookId())));

	}

	@Test
	public void checaSeOsLivrosEmprestadosEstaoSendoListados() {
		
		User user = new User("name");
		Book book = new Book("title", "author");
		LocalDate loanDate = LocalDate.now();
		LocalDate returnDate = LocalDate.now().plusDays(7);

		User user2 = new User("name2");
		Book book2 = new Book("title2", "author2");
		LocalDate loanDate2 = LocalDate.now();
		LocalDate returnDate2 = LocalDate.now().plusDays(7);

		LoanDAO loanDAO = new LoanDAO();
		loanDAO.addLoan(user, book, loanDate, returnDate);
		loanDAO.addLoan(user2, book2, loanDate2, returnDate2);

		List<Loan> loans = loanDAO.getLoanList();

		Assert.assertEquals(loanDAO.getLoanList().get(0), loans.get(0));
		Assert.assertEquals(loanDAO.getLoanList().get(1), loans.get(1));
	}
}
