package com.basaki.example.resource;

import com.basaki.example.data.dao.BookDAO;
import com.basaki.example.data.entity.Book;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    private final BookDAO dao;

    public BookResource(BookDAO dao) {
        this.dao = dao;
    }

    @ApiOperation(
            value = "Creates a book",
            response = Book.class)
    @POST
    @UnitOfWork
    public Book create(@Valid Book book) {
        return dao.create(book);
    }

//    @ApiOperation(
//            value = "Retrieves a book based on book ID",
//            response = Book.class)
//    @GET
//    @UnitOfWork
//    public Book read(@PathParam("id") @ApiParam(value = "Book ID") UUID id) {
//        return dao.read(id).get();
//    }

    @ApiOperation(
            value = "List of all books.",
            response = Book.class, responseContainer = "List")
    @GET
    @UnitOfWork
    public List<Book> readAll() {
        return dao.read();
    }
}
