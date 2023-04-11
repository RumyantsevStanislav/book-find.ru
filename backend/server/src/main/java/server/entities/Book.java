package server.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "labirint_id")
    private Long labirintId;
    // TODO: 17.11.2022 use limitations on each fields of each entities
    //@Column(nullable = false, updatable = false, unique = true, length = 50)
    private String title;

    private String description;

    private Integer price;

    private Integer pages;

    private Integer year;

    private Float estimation;

    @Column(name = "estimations_count")
    private Integer estimationsCount;

    @NaturalId
    // TODO: 06.02.2023 is it useful?
    //@ISBN
    private Long isbn;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private Set<Isbn> isbns = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book")
    //@Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    private Set<Review> reviews = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToMany
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @ToString.Exclude
    private Set<Category> categories;

    @ManyToMany
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @ToString.Exclude
    private Set<Author> authors;

    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToOne/*(cascade = {CascadeType.ALL}, orphanRemoval = true)*/
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "series_id")
    private Series series;

    @OneToOne
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "cover_id")
    private Cover cover;

    // TODO: 17.11.2022 is it really need?

    @OneToMany(mappedBy = "book")
    @Getter(AccessLevel.NONE)
    @ToString.Exclude
    private Set<PersonalBook> personalBooks = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return id != null && Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @AllArgsConstructor
    @Getter
    public enum Status {
        ACTIVE, HIDDEN;
    }
}
