package com.trilogyed.bookservice.serviceLayer;

import com.trilogyed.bookservice.dao.BookDao;
import com.trilogyed.bookservice.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceLayer {
    private BookDao bookDao;

    @Autowired
    public ServiceLayer(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public Book saveBook(Book book){
        return bookDao.saveBook(book);
    }

    public Book getBook(int book_id){
        return bookDao.getBook(book_id);
    }

    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }
}
