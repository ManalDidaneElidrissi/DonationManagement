package myblood.repository;

import myblood.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository <Utilisateur, Long> {
    List<Utilisateur> findByStatus(boolean status);

    Utilisateur findByEmail(String email);

}
