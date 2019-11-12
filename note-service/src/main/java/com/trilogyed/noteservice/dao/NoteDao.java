package com.trilogyed.noteservice.dao;

import com.trilogyed.noteservice.model.Note;

import java.util.List;

public interface NoteDao {

    Note getNoteById(int id);

    List<Note> getNotesByBook(int book_id);

    List<Note> getAllNotes();

    void updateNote(int id);

    void deleteNote(int id);
}
