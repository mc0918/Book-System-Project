package com.trilogyed.noteservice.controller;

import com.trilogyed.noteservice.dao.NoteDao;
import com.trilogyed.noteservice.model.Note;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class NoteController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private NoteDao noteDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public NoteController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private RestTemplate restTemplate = new RestTemplate();




    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Note createNote(@RequestBody @Valid Note note) {


        return noteDao.addNote(note);
    }

    @RequestMapping(value = "/notes/queue", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Note createNoteFromQueue() {

        Object noteFromQueue = rabbitTemplate.receiveAndConvert("note-queue");
        System.out.println("Strung cheese....   " + noteFromQueue);

        return null;
    }

    @GetMapping("/notes/{note_id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Note findNoteById(@PathVariable int note_id) {
        if (note_id < 1) {
            throw new IllegalArgumentException("note id must be greater than 0.");
        }
        return noteDao.getNoteById(note_id);
    }

    @GetMapping("/notes/book/{book_id}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Note> findNotesByBook(@PathVariable int book_id) {
        if (book_id < 1) {
            throw new IllegalArgumentException("book id must be greater than 0.");
        }
        return noteDao.getNotesByBook(book_id);
    }

    @GetMapping("/notes")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Note> findAllNotes() {
        return noteDao.getAllNotes();
    }

    @PutMapping("/notes")
    @ResponseStatus(HttpStatus.OK)
    public void updateNote(Note note) {
        noteDao.updateNote(note);

    }

    @DeleteMapping("/notes/{note_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable int note_id) {
        noteDao.deleteNote(note_id);

    }

    //rabbit thing here
    //recieve?
    //store in db
}
