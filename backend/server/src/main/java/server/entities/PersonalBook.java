package server.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "personal_books", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"isbn", "phone", "email"})
})
public class PersonalBook extends DefaultEntity implements Serializable {

    private String isbn;
    private String phone;
    private String email;
    private String status;
    private Integer estimation;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "isbn", referencedColumnName = "isbn", insertable = false, updatable = false)
    private Book book;

    @ManyToOne
    @Transient
    @JoinColumns(
            {
                    @JoinColumn(name = "phone", referencedColumnName = "phone", insertable = false, updatable = false),
                    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
            }
    )
    private User user;

}
