package myblood;

import myblood.model.Admin;
import myblood.model.Utilisateur;
import myblood.repository.AdminRepository;
import myblood.repository.UserRepository;
import myblood.security.roleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class GestionDonsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionDonsApplication.class, args);
	}
	@Bean
	PasswordEncoder pwdEncod(){
		return new BCryptPasswordEncoder();
	}

	//@Bean
	CommandLineRunner commandLineRunneradd(AdminRepository adminRepository) {
		return args -> {
			adminRepository.save(
					new Admin("Mdi_25", "manaldidane125@gmail.com", "0000"));
		};

	}

	//@Bean
	CommandLineRunner commandLineRunnerAddRole (roleService roleService,UserRepository userRepository) {
		return args -> {
			roleService.addRole("ADMIN");
			roleService.addRole("USER");

			List<Utilisateur> utilisateurs = userRepository.findAll();
			for (Utilisateur utilisateur : utilisateurs) {
				roleService.addRoleToUser(utilisateur.getEmail(), "USER");
				//roleService.addRoleToUser("manaldidane125@gmail.com", "ADMIN");
			};

		};
	}
	/*@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository) {
		return args -> {
			userRepository.save(new Utilisateur("Jihane JIHO", "O+","Femme","0600000000","jihne@gmail.com","0000","Casablanca",true));
			userRepository.save(new Utilisateur("Safaa MACHKOUR", "O+","Femme","0600000000","safaamach@gmail.com","0000","Rabat",true));
			userRepository.save(new Utilisateur("Oussame AMINE", "O+","Homme","0600000000","ussa@gmail.com","0000","Fes",true));
			userRepository.save(new Utilisateur("Ismail SABRI", "O+","Homme","0600000000","ismailsb@gmail.com","0000","Fes",true));
			userRepository.save(new Utilisateur("Amina ZINO", "O+","Femme","0600000000","aminazn@gmail.com","0000","Casablanca",true));

		};

	}*/
}
