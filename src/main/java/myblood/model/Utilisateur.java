package myblood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import myblood.security.SecRole;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Username")
    private String Username;
    @Column(name = "groupeSanguin")
    private String groupeSanguin;
    @Column(name = "sexe")
    private String sexe;
    @Column(name = "numTel")
    private String numTel;
    @Column(name = "email")
    private String email;
    @Column(name = "password") @Size(min = 4)
    private String password;
    @Column(name = "ville")
    private String ville;
    @Column(name = "status")
    private boolean status = false;
    @Column(name = "don") @Temporal(TemporalType.DATE) @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date don;


    @ManyToMany (fetch = FetchType.EAGER)
    private List<SecRole> roles;

    @OneToMany(mappedBy = "user")
    private List<Demande> demande;


    public Utilisateur(String username, String groupeSanguin, String sexe, String numTel, String email, String password, String ville, boolean status) {
        Username = username;
        this.groupeSanguin = groupeSanguin;
        this.sexe = sexe;
        this.numTel = numTel;
        this.email = email;
        this.password = password;
        this.ville = ville;
        this.status = status;
    }
}
