package com.trilogyed.noteservice.dao;

import com.trilogyed.noteservice.model.Note;

import java.util.List;

public class NoteDaoJdbcTemplateImpl implements NoteDao {
    @Override
    public Note getNoteById(int id) {
        return null;
    }

    @Override
    public List<Note> getNotesByBook(int book_id) {
        return null;
    }

    @Override
    public List<Note> getAllNotes() {
        return null;
    }

    @Override
    public void updateNote(int id) {

    }

    @Override
    public void deleteNote(int id) {

    }
}
