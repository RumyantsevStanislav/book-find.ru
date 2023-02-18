package server.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "isbns")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Isbn extends DefaultEntity {

    private Long isbn;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

}
