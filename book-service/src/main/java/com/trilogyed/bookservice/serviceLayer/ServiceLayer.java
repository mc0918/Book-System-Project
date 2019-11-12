package com.trilogyed.bookservice.serviceLayer;

import com.trilogyed.bookservice.dao.BookDao;
import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.util.feign.NoteLookupClient;
import com.trilogyed.bookservice.viewModel.BookViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ServiceLayer {
    private BookDao bookDao;

    @Autowired
    public ServiceLayer(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public BookViewModel saveBook(BookViewModel bvm){
        Book b = new Book(bvm.getTitle(), bvm.getAuthor());
        b = bookDao.saveBook(b);
        bvm.setBook_id(b.getBook_id());

        return bvm;
    }

    public BookViewModel getBook(int book_id){
        Book b = bookDao.getBook(book_id);
        BookViewModel bvm = new BookViewModel(b.getBook_id(), b.getTitle(), b.getAuthor());
        return bvm;
    }

    public List<BookViewModel> getAllBooks() {
        List<Book> bookList = bookDao.getAllBooks();
        List<BookViewModel> bookViewModelList = new ArrayList<>();
        for (Book i : bookList){
            BookViewModel ivmTemp = buildViewModel(i);
            bookViewModelList.add(ivmTemp);
        }
        return bookViewModelList;
    }

    public void updateBook(BookViewModel bvm) {
        Book b = new Book(bvm.getBook_id(), bvm.getTitle(), bvm.getAuthor());
        bookDao.updateBook(b);
    }

    public void deleteBook(int book_id){
        bookDao.deleteBook(book_id);
    }

    public BookViewModel buildViewModel(Book b){
        BookViewModel bvm = new BookViewModel(b.getBook_id(), b.getTitle(), b.getAuthor());
        return bvm;
    }
}
