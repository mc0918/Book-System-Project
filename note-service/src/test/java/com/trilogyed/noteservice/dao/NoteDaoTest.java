package com.trilogyed.noteservice.dao;

import com.trilogyed.noteservice.model.Note;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NoteDaoTest {

    @Autowired
    protected NoteDao noteDao;

    @Before
    public void setUp() throws Exception {
        List<Note> notes = noteDao.getAllNotes();
        notes.stream().forEach(note -> noteDao.deleteNote(note.getNote_id()));
    }

    @Test
    public void addGetDeleteNote() {
        Note noteToAdd = new Note();
        noteToAdd.setBook_id(1);
        noteToAdd.setNote("This is the test note");

        Note noteFromDb = noteDao.addNote(noteToAdd);
        noteToAdd.setNote_id(noteFromDb.getNote_id());

        assertEquals(noteToAdd, noteFromDb);
        assertEquals(noteToAdd, noteDao.getNoteById(noteToAdd.getNote_id()));

        noteDao.deleteNote(noteToAdd.getNote_id());

        assertNull(noteDao.getNoteById(noteToAdd.getNote_id()));
    }


    @Test
    public void getNotesByBook() {
        Note noteToAdd = new Note();
        noteToAdd.setBook_id(1);
        noteToAdd.setNote("This is the test note");

        Note noteToAdd2 = new Note();
        noteToAdd2.setBook_id(2);
        noteToAdd2.setNote("This is the second test note");

        Note noteToAdd3 = new Note();
        noteToAdd3.setBook_id(2);
        noteToAdd3.setNote("This is the third test note");

        Note noteFromDb = noteDao.addNote(noteToAdd);
        noteToAdd.setNote_id(noteFromDb.getNote_id());

        Note noteFromDb2 = noteDao.addNote(noteToAdd2);
        noteToAdd2.setNote_id(noteFromDb2.getNote_id());

        Note noteFromDb3 = noteDao.addNote(noteToAdd3);
        noteToAdd3.setNote_id(noteFromDb3.getNote_id());

        List<Note> listWeExpect = new ArrayList<>(Arrays.asList(noteToAdd2, noteToAdd3));
        List<Note> listFromDb = noteDao.getNotesByBook(2);

        assertEquals(listWeExpect, listFromDb);
    }

    @Test
    public void getAllNotes() {
        Note noteToAdd = new Note();
        noteToAdd.setBook_id(1);
        noteToAdd.setNote("This is the test note");

        Note noteToAdd2 = new Note();
        noteToAdd2.setBook_id(2);
        noteToAdd2.setNote("This is the second test note");

        Note noteToAdd3 = new Note();
        noteToAdd3.setBook_id(2);
        noteToAdd3.setNote("This is the third test note");

        Note noteFromDb = noteDao.addNote(noteToAdd);
        noteToAdd.setNote_id(noteFromDb.getNote_id());

        Note noteFromDb2 = noteDao.addNote(noteToAdd2);
        noteToAdd2.setNote_id(noteFromDb2.getNote_id());

        Note noteFromDb3 = noteDao.addNote(noteToAdd3);
        noteToAdd3.setNote_id(noteFromDb3.getNote_id());

        List<Note> listWeExpect = new ArrayList<>(Arrays.asList(noteToAdd, noteToAdd2, noteToAdd3));
        List<Note> listFromDb = noteDao.getAllNotes();

        assertEquals(listWeExpect, listFromDb);
    }

    @Test
    public void updateNote() {
        Note noteToAdd = new Note();
        noteToAdd.setBook_id(1);
        noteToAdd.setNote("This is the test note");

        Note noteToAdd2 = new Note();
        noteToAdd2.setBook_id(2);
        noteToAdd2.setNote("This is the second test note");

        Note noteToAdd3 = new Note();
        noteToAdd3.setBook_id(2);
        noteToAdd3.setNote("This is the third test note");

        Note noteFromDb = noteDao.addNote(noteToAdd);
        noteToAdd.setNote_id(noteFromDb.getNote_id());

        Note noteFromDb2 = noteDao.addNote(noteToAdd2);
        noteToAdd2.setNote_id(noteFromDb2.getNote_id());

        Note noteFromDb3 = noteDao.addNote(noteToAdd3);
        noteToAdd3.setNote_id(noteFromDb3.getNote_id());

        Note updatedNote = new Note();
        updatedNote.setNote_id(noteToAdd.getNote_id());
        updatedNote.setBook_id(55);
        updatedNote.setNote("This is the updated note");

        noteDao.updateNote(updatedNote);

        assertEquals(updatedNote, noteDao.getNoteById(noteToAdd.getNote_id()));

    }

}