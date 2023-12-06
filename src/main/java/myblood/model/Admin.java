package myblood.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admin")
public class Admin {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "matricule")
    private String matricule;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

    @OneToMany
    private Collection<Demande> demandes = new HashSet<>();
    @OneToMany
    private Collection<Utilisateur> users = new HashSet<>();

    public Admin(String matricule, String login, String pwd) {
        this.matricule = matricule;
        this.login = login;
        this.password = pwd;
    }
}
