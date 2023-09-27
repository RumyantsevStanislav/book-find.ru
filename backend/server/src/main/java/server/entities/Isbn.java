package server.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "isbns")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Isbn extends DefaultEntity {

    private String isbn;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

}
