package server.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "series")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "labirint_id")
    private Long labirintId;

    @NaturalId
    private String title;

    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "series")
    @ToString.Exclude
    @Getter(AccessLevel.NONE)
    private List<Book> books;
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
        Series series = (Series) o;
        return id != null && Objects.equals(id, series.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
