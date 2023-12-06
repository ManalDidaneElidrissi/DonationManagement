package myblood.security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface roleRepo extends JpaRepository<SecRole, String> {
}
