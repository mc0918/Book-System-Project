package com.trilogyed.bookservice.util.feign;

import com.trilogyed.bookservice.util.messages.NoteListEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "note-service")
public interface NoteLookupClient {
    @RequestMapping(value = "/notes/book/{book_id}", method = RequestMethod.GET)
    public List<NoteListEntry> getNotesByBook(@PathVariable int book_id );

    //Findall
    @DeleteMapping("/notes/{note_id}")
    public void deleteNote(@PathVariable int note_id);
    //Delete
}