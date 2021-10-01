package com.basaki.example.data.dao;

import com.basaki.example.data.entity.Book;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookDAO extends AbstractDAO<Book> {

    public BookDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Book create(Book book) {
        return persist(book);
    }

    public Optional<Book> read(UUID id) {
        return Optional.ofNullable(get(id));
    }

    public List<Book> read() {
        return list(namedTypedQuery("com.basaki.example.data.entity.Book.read"));
    }
}
