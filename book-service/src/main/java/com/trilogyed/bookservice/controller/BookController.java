package com.trilogyed.bookservice.controller;

import com.trilogyed.bookservice.model.Book;
import com.trilogyed.bookservice.serviceLayer.ServiceLayer;
import com.trilogyed.bookservice.util.feign.NoteLookupClient;
import com.trilogyed.bookservice.util.messages.NoteListEntry;
import com.trilogyed.bookservice.viewModel.BookViewModel;
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
    private NoteLookupClient client; //removed final

    public BookController(NoteLookupClient client) {
        this.client = client;
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public BookController(){} //why is this necessary?

    public BookController(RabbitTemplate rabbitTemplate, NoteLookupClient noteLookupClient) {
        this.client = noteLookupClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    private ServiceLayer serviceLayer;

    private RestTemplate restTemplate;

    public static final String EXCHANGE = "note-exchange";
    public static final String ROUTING_KEY = "note.add.book.controller";

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public BookViewModel addBook(@RequestBody @Valid BookViewModel b){
        b = serviceLayer.saveBook(b);

        final int bookId = b.getBook_id();

        b.getNotes().forEach(note -> {
            note.setBook_id(bookId);

            System.out.println("Sending note: " + note.getNote());

            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, note); //maybe one note at a time instead of a list?
            System.out.println("Note sent :)");
        });


        return b;
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public BookViewModel getBook(@PathVariable @Valid int id) throws IllegalArgumentException{
        if (id < 1 ){
            throw new IllegalArgumentException("enter a valid ID number");
        }
        return serviceLayer.getBook(id);
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<BookViewModel> getAllBooks(){
        return serviceLayer.getAllBooks();
    }

    @RequestMapping(value = "/books/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateBook(@RequestBody @Valid BookViewModel b) throws IllegalArgumentException{
        if (b.getBook_id() < 1){
            throw new IllegalArgumentException("book must have a valid id");
        }
        serviceLayer.updateBook(b);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable @Valid int id) throws IllegalArgumentException {
        if (id < 1){
            throw new IllegalArgumentException("enter a valid id");
        }
        serviceLayer.deleteBook(id);
    }



    @RequestMapping(value="/notes/book/{book_id}", method = RequestMethod.GET)
    public BookViewModel getNotes(@PathVariable @Valid int book_id) {
        List<NoteListEntry> listEntries = client.getNotesByBook(book_id);
        BookViewModel modelToUpdate = serviceLayer.getBook(book_id);
        modelToUpdate.setNotes(listEntries);
        return modelToUpdate;
    }



    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    public String createNote(NoteListEntry noteListEntry) {
        //book.getBookId()
        return null;
    }
}
