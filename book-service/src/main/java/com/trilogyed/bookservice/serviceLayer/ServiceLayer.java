package com.trilogyed.bookservice.serviceLayer;

import com.trilogyed.bookservice.dao.BookRepository;
import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.viewModel.BookViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Component
@Service
public class ServiceLayer {
//    private BookDao bookDao;
    private BookRepository bookRepository;

    @Autowired
    public ServiceLayer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookViewModel saveBook(BookViewModel bvm){
        Book b = new Book(bvm.getTitle(), bvm.getAuthor());
        b = bookRepository.save(b);
        bvm.setBook_id(b.getBook_id());

        return bvm;
    }

    public BookViewModel getBook(int book_id){

        BookViewModel bvm = new BookViewModel();

        bookRepository.findById(book_id)
                .ifPresent(b -> {
                    bvm.setBook_id(b.getBook_id());
                    bvm.setTitle(b.getTitle());
                    bvm.setAuthor(b.getAuthor());
                });
        return bvm;
    }

    public List<BookViewModel> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        List<BookViewModel> bookViewModelList = new ArrayList<>();
        for (Book i : bookList){
            BookViewModel ivmTemp = buildViewModel(i);
            bookViewModelList.add(ivmTemp);
        }
        return bookViewModelList;
    }

    public void updateBook(BookViewModel bvm) {
        Book b = new Book(bvm.getBook_id(), bvm.getTitle(), bvm.getAuthor());
        bookRepository.save(b);
    }

    public void deleteBook(int book_id){
        bookRepository.deleteById(book_id);
    }

    public BookViewModel buildViewModel(Book b){
        BookViewModel bvm = new BookViewModel(b.getBook_id(), b.getTitle(), b.getAuthor());
        return bvm;
    }
}
