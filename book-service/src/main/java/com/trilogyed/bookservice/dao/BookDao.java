package com.trilogyed.bookservice.dao;

import com.trilogyed.bookservice.model.Book;

import java.util.List;

public interface BookDao {
    Book saveBook(Book book);
    Book getBook(int book_id);
    List<Book> getAllBooks();
    void updateBook(Book book);
    void deleteBook(int book_id);
}
