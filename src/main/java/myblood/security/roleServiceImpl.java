package myblood.security;

import lombok.AllArgsConstructor;
import myblood.model.Utilisateur;
import myblood.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional @AllArgsConstructor
public class roleServiceImpl implements roleService {
    private roleRepo roleRepo;
    private UserRepository userRepository;
    @Override
    public SecRole addRole(String role) {
        SecRole secRole = roleRepo.findById(role).orElse(null);
        if(secRole!=null) throw new RuntimeException("Role already exists");
        secRole= SecRole.builder().role(role).build();
        return roleRepo.save(secRole);
    }

    @Override
    public void addRoleToUser(String login, String role) {
        Utilisateur user = userRepository.findByEmail(login);
        SecRole Role = roleRepo.findById(role).get();
        user.getRoles().add(Role);
    }
}
