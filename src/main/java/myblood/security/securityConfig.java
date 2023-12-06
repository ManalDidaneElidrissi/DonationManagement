package myblood.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration @EnableWebSecurity @EnableMethodSecurity(prePostEnabled = true)
public class securityConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpsecurity) throws Exception {

        httpsecurity.authorizeHttpRequests((requests) -> requests.requestMatchers("/css/**","/imgs/**","/js/**","/","/docs","/login","/Sign_Up","/save").permitAll()
                        .requestMatchers("/home","/requests","/profile","/update","/adminSpace","/addUser","/addReq","/deleteUser","/deleteReq","/articles","/BloodTypes","/DonationsBenefits","/Advantages_Disadvantages","/howtodonate").permitAll().anyRequest().authenticated())
                .formLogin().defaultSuccessUrl("/home",true).loginPage("/login").permitAll();
       // httpsecurity.logout().logoutSuccessUrl("/");
       // httpsecurity.userDetailsService(userDetailService);
        return httpsecurity.build();
    }

   // @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        return manager;
    }

   // @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        String pwd = passwordEncoder.encode("12345");
        System.out.println(pwd);
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(

                User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("user2").password(pwd).roles("USER").build(),
                User.withUsername("admin").password(pwd).roles("ADMIN","USER").build()
        );
        return  manager;
    }

}
