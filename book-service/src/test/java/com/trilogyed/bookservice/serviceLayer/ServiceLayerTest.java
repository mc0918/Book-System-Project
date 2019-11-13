package com.trilogyed.bookservice.serviceLayer;

import com.trilogyed.bookservice.dao.BookRepository;
import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.viewModel.BookViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceLayerTest {
    private ServiceLayer serviceLayer;
    private BookRepository bookRepository;

    @Before
    public void setUp() throws Exception {
        setUpBookMock();
        serviceLayer = new ServiceLayer(bookRepository);
    }

    public void setUpBookMock(){
        bookRepository = mock(BookRepository.class);

        Book bookToAdd = new Book("Test Book", "Tester Bookington");
        Book bookToReturn = new Book(1,"Test Book", "Tester Bookington");

        List<Book> bookListToReturn = new ArrayList<>(Arrays.asList(bookToReturn));

        Optional<Book> optionalBook = Optional.of(bookToReturn);

        doReturn(bookToReturn).when(bookRepository).save(bookToAdd);
        doReturn(optionalBook).when(bookRepository).findById(1);
        doReturn(bookListToReturn).when(bookRepository).findAll();
    }

    @Test
    public void saveBook() {
        BookViewModel bvmtoAdd = new BookViewModel();
        bvmtoAdd.setTitle("Test Book");
        bvmtoAdd.setAuthor("Tester Bookington");

        BookViewModel bvmFromDb = serviceLayer.saveBook(bvmtoAdd);
        bvmtoAdd.setBook_id(bvmFromDb.getBook_id());

        assertEquals(bvmtoAdd, bvmFromDb);
    }

    @Test
    public void getBook() {
        BookViewModel bvmtoAdd = new BookViewModel();
        bvmtoAdd.setTitle("Test Book");
        bvmtoAdd.setAuthor("Tester Bookington");
        bvmtoAdd.setBook_id(1);

        BookViewModel bvmExpected = serviceLayer.getBook(bvmtoAdd.getBook_id());

        assertEquals(bvmtoAdd, bvmExpected);
    }

    @Test
    public void getAllBooks() {
        BookViewModel bvmtoAdd = new BookViewModel();
        bvmtoAdd.setTitle("Test Book");
        bvmtoAdd.setAuthor("Tester Bookington");
        bvmtoAdd.setBook_id(1);

        List<BookViewModel> listWeExpect = new ArrayList<>(Arrays.asList(bvmtoAdd));
        List<BookViewModel> listFromDb = serviceLayer.getAllBooks();

        assertEquals(listWeExpect, listFromDb);
    }

}