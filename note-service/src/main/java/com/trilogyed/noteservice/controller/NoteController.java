package com.trilogyed.noteservice.controller;

import com.trilogyed.noteservice.dao.NoteDao;
import com.trilogyed.noteservice.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Random;

@RestController
@RefreshScope
public class NoteController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private NoteDao noteDao;

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Note createMotorcycle(@RequestBody @Valid Note note) {
        return noteDao.addNote(note);
    }

    @GetMapping("/notes/{book_id}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Note> findNotesByBook(@PathVariable int book_id) {
        if (book_id < 1) {
            throw new IllegalArgumentException("book id must be greater than 0.");
        }
        return noteDao.getNotesByBook(book_id);
    }
}
