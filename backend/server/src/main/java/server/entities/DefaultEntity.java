package server.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
// TODO: 17.11.2022 researc (force = true) все final поля инициализируются 0 / false / null. Для полей с ограничениями, например @NotNull, никаких проверок при присвоении не генерируется, поэтому эти условия могут не выполняться, пока эти поля не будут инициализированы подобающим образом позже.
@MappedSuperclass
@AllArgsConstructor
public class DefaultEntity {
    @Id
    // TODO: 17.11.2022 research @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", initialValue = 1, allocationSize = 2)
    @GeneratedValue(strategy = GenerationType.IDENTITY/*SEQUENCE, generator = "user_seq"*/)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime updated_at;

    @PrePersist
    public void onCreate() {
        this.created_at = OffsetDateTime.now();
        this.updated_at = OffsetDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updated_at = OffsetDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DefaultEntity that = (DefaultEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
