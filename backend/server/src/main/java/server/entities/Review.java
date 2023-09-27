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
@Table(name = "reviews")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Review extends DefaultEntity {

    private String review;

    private Short estimation;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

}
