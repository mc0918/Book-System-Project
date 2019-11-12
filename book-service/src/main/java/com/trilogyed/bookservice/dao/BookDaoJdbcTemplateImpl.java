package com.trilogyed.bookservice.dao;

import com.trilogyed.bookservice.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookDaoJdbcTemplateImpl implements BookDao{
    private static final String SAVE_BOOK_SQL =
            "insert into book (title, author) values (?,?)";

    private static final String GET_BOOK_SQL =
            "select * from book where book_id = ?";

    private static final String GET_ALL_BOOKS_SQL =
            "select * from book";

    private static final String UPDATE_BOOK_SQL =
            "uodate book set title = ?, author = ? where book_id = ?";

    private static final String DELETE_BOOK_SQL =
            "delete from book where book_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book saveBook(Book book) {
        jdbcTemplate.update(SAVE_BOOK_SQL,book.getTitle(), book.getAuthor());
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        book.setBook_id(id);

        return book;
    }

    @Override
    public Book getBook(int book_id) {
        try{
            return jdbcTemplate.queryForObject(GET_BOOK_SQL, this::mapRowToBook, book_id);
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbcTemplate.query(GET_ALL_BOOKS_SQL, this::mapRowToBook);
    }

    @Override
    public void updateBook(Book book) {
        jdbcTemplate.update(
                UPDATE_BOOK_SQL,
                book.getTitle(),
                book.getAuthor(),
                book.getBook_id()
        );
    }

    @Override
    public void deleteBook(int book_id) {
        jdbcTemplate.update(DELETE_BOOK_SQL, book_id);
    }

    private Book mapRowToBook(ResultSet rs, int rowNum) throws SQLException {
        Book b = new Book(
                rs.getInt("book_id"),
                rs.getString("title"),
                rs.getString("author")
                );
        return b;
    }
}
