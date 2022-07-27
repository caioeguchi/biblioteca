package com.caionilson.core.tema8;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.caionilson.core.tema8.dao.BookDAO;
import com.caionilson.core.tema8.model.Book;

import junit.framework.Assert;

public class BookDAOTest {

	@Test
	public void isAddingBooks() {
		String title = "book";
		String author = "author";

		Book book = new Book(title, author);

		BookDAO bookDAO = new BookDAO();
		bookDAO.addBook(book);

		Assert.assertEquals("book", book.getBookTitle());
		Assert.assertEquals("author", book.getBookAuthor());
	}

	@Test
	public void isListingAllBooks() {
		BookDAO books = new BookDAO();

		Book book1 = new Book(1, "book1", "Author1");
		Book book2 = new Book(2, "book2", "Author2");
		Book book3 = new Book(3, "book3", "Author3");
		books.addBook(book1);
		books.addBook(book2);
		books.addBook(book3);

		List<Book> bookList = books.getBookList();

		List<Book> expected = new ArrayList<>();
		expected.add(new Book(1, "book1", "author1"));
		expected.add(new Book(2, "book2", "author2"));
		expected.add(new Book(3, "book3", "author3"));

		Assert.assertEquals(expected.get(0).getBookTitle(), bookList.get(0).getBookTitle());
		Assert.assertEquals(expected.get(1).getBookTitle(), bookList.get(1).getBookTitle());
		Assert.assertEquals(expected.get(2).getBookTitle(), bookList.get(2).getBookTitle());
	}

	@Test
	public void gettingBookId() {
		Book book = new Book(1, "book1", "author1");

		BookDAO bookDAO = new BookDAO();

		bookDAO.addBook(book);

		Assert.assertEquals(bookDAO.getBookList().get(0), bookDAO.getBookId(1));
	}

	@Test
	public void booksAreBeingRemoved() {
		int id = 1;
		BookDAO bookDAO = new BookDAO();
		Book book1 = new Book(1, "book1", "Author1");

		bookDAO.addBook(book1);
		bookDAO.removeBook(id);
		;

		Assert.assertTrue(!bookDAO.getBookList().contains(bookDAO.getBookId(id)));
	}

}
