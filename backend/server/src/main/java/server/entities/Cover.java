package server.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name = "covers")
@Data
@NoArgsConstructor
public class Cover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private Blob image;

    @Column(name = "extension")
    private String extension;

    @Column(name = "path")
    private String path;
}
