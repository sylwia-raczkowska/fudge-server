package hello.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Data
@Builder
@Table(name="links")
public class Links {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieId")
    private Integer movieId;

    @Column(name = "imdbId")
    private String imdbId;

    @Column(name = "tmdbId")
    private String tmdbId;
}
