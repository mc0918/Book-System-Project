package com.trilogyed.notequeueconsumer.util.feign;

import com.trilogyed.notequeueconsumer.util.messages.NoteEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@FeignClient(name = "note-service")
public interface NoteClient {
//    @RequestMapping(value = "/notes/book/{book_id}", method = RequestMethod.GET)
//    public List<NoteEntry> getNotesByBook(@PathVariable int book_id);

    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    public NoteEntry saveNoteToDb(NoteEntry noteEntry);

    @RequestMapping(value = "/notes", method = RequestMethod.PUT)
    public NoteEntry updateNoteinDb(NoteEntry noteEntry);
}

