package com.trilogyed.bookservice.controller;

import com.netflix.ribbon.proxy.annotation.Http;
import com.trilogyed.bookservice.dao.BookDao;
import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.util.feign.NoteLookupClient;
import com.trilogyed.bookservice.util.messages.NoteListEntry;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
public class BookController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private BookDao bookDao;

    private RestTemplate restTemplate;

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody @Valid Book book){
        return bookDao.saveBook(book);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Book getBook(@PathVariable @Valid int id) throws IllegalArgumentException{
        if (id < 1 ){
            throw new IllegalArgumentException("enter a valid ID number");
        }
        return bookDao.getBook(id);
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooks(){
        return bookDao.getAllBooks();
    }

    @RequestMapping(value = "/books/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateBook(@RequestBody @Valid Book b) throws IllegalArgumentException{
        if (b.getBook_id() < 1){
            throw new IllegalArgumentException("book must have a valid id");
        }
        bookDao.updateBook(b);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable @Valid int id) throws IllegalArgumentException {
        if (id < 1){
            throw new IllegalArgumentException("enter a valid id");
        }
        bookDao.deleteBook(id);
    }

    @Autowired
    private final NoteLookupClient client;

    BookController(NoteLookupClient client) {
        this.client = client;
    }

    @RequestMapping(value="/notes/book/{book_id}", method = RequestMethod.GET)
    public List<Map<String, String>> getNotes(@PathVariable @Valid int book_id) {
        return client.getNotesByBook(book_id);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public BookController(RabbitTemplate rabbitTemplate, NoteLookupClient noteLookupClient) {
        this.client = noteLookupClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    public String createNote(NoteListEntry noteListEntry) {
        //book.getBookId()
        return null;
    }
}
