package myblood.repository;

import myblood.model.Demande;
import myblood.model.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
    List<Demande> findByPub(boolean pub);



    Page<Demande> findByGroupeSanguinContainsAndPub(String type, boolean pub, Pageable pageable);


    List<Demande> findByUserAndPub(Utilisateur user, boolean pub);
}
