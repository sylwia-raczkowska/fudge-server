package hello.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Data
@Builder
@Table(name="movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieId")
    private Integer movieId;

    @Column(name = "title")
    private String title;

    @Column(name = "genres")
    private String genres;

    @OneToOne
    Links links;
}
