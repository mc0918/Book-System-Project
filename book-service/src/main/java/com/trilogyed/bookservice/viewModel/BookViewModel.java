package com.trilogyed.bookservice.viewModel;

import com.trilogyed.bookservice.util.messages.NoteListEntry;

import java.util.List;
import java.util.Objects;

public class BookViewModel {
    private int book_id;
    private String title;
    private String author;
    private List<NoteListEntry> notes;

    public BookViewModel(String title, String author, List<NoteListEntry> notes) {
        this.title = title;
        this.author = author;
        this.notes = notes;
    }

    public BookViewModel(int book_id, String title, String author, List<NoteListEntry> notes) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.notes = notes;
    }

    public BookViewModel(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public BookViewModel(int book_id, String title, String author) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
    }

    public BookViewModel() {
    }

    @Override
    public String toString() {
        return "BookViewModel{" +
                "book_id=" + book_id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", notes=" + notes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookViewModel that = (BookViewModel) o;
        return book_id == that.book_id &&
                title.equals(that.title) &&
                author.equals(that.author) &&
                Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book_id, title, author, notes);
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<NoteListEntry> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteListEntry> notes) {
        this.notes = notes;
    }
}
