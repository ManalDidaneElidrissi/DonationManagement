package myblood.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Entity @AllArgsConstructor @NoArgsConstructor
@Table(name = "demande")
public class Demande {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "seekerName")
    private String seekerName;
    @Column(name = "groupeSanguin")
    private String groupeSanguin;
    @Column(name = "numTel")
    private String numTel;
    @Column(name = "ville")
    private String ville;
    @Column(name = "nomHopital")
    private String nomHopital;
    @Column(name = "urgence")
    private Boolean urgence = false;
    @Column(name = "nbPoche")
    private Integer nbPoche;
    @Column(name = "pub")
    private Boolean pub = false;


    @ManyToOne
    public Utilisateur user;
}
