package com.trilogyed.noteservice.dao;

import com.trilogyed.noteservice.model.Note;

import java.util.List;

public interface NoteDao {

    Note addNote(Note note);

    Note getNoteById(int id);

    List<Note> getNotesByBook(int book_id);

    List<Note> getAllNotes();

    void updateNote(Note note);

    void deleteNote(int id);
}
