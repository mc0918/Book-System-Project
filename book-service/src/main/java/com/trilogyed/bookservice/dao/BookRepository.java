package com.trilogyed.bookservice.dao;

import com.trilogyed.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //do we need this?
public interface BookRepository extends JpaRepository<Book, Integer> {
}
