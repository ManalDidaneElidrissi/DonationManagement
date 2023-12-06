package myblood.security;

import lombok.AllArgsConstructor;
import myblood.model.Utilisateur;
import myblood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private roleService roleServ;
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Utilisateur user = userRepository.findByEmail(login);
        System.out.println("test");
        if(user==null) throw new UsernameNotFoundException("User not found");
        UserDetails userDetails = User
                .withUsername(user.getEmail())
                .passwordEncoder(passwordEncoder::encode)
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(u->u.getRole()).toArray(String[]::new))
                .build();
        return userDetails;
    }

    /*
    * @Autowired
private PasswordEncoder passwordEncoder;

public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Utilisateur user = userRepository.findByEmail(username);
    if(user==null) throw new UsernameNotFoundException("User not found");
    UserDetails userDetails = User
            .withUsername(user.getEmail())
            .passwordEncoder(passwordEncoder::encode) // Utiliser l'encodeur pour le mot de passe
            .password(user.getPassword())
            .roles(user.getRoles().stream().map(u->u.getRole()).toArray(String[]::new))
            .build();
    return userDetails;
}
*/
}
