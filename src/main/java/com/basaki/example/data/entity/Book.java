package com.basaki.example.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * {@code Book} represents a book entity.
 * <p>
 *
 * @author Indra Basak
 * @since 09/30/21
 */
@Entity
@Table(name = "book")
@Getter
@Setter
@ApiModel(value = "Book")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedQueries({
        @NamedQuery(
                name = "com.basaki.example.data.entity.Book.read",
                query = "SELECT b FROM Book b"
        )
})
public class Book implements Serializable {

    @ApiModelProperty(value = "identity of a book")
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ApiModelProperty(value = "book title")
    @Column(name = "title", nullable = false)
    private String title;

    @ApiModelProperty(value = "book author")
    @Column(name = "author", nullable = false)
    private String author;
}

