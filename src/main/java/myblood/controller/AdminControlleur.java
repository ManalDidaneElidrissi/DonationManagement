package myblood.controller;

import myblood.model.Admin;
import myblood.model.Demande;
import myblood.model.Utilisateur;
import myblood.repository.AdminRepository;
import myblood.repository.DemandeRepository;
import myblood.repository.UserRepository;
import myblood.security.roleRepo;
import myblood.security.roleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class AdminControlleur {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private roleService roleServ;



    @GetMapping(path = "/adminSpace")
    @PreAuthorize("hasRole('ADMIN')")
    public String Users(Model model, Authentication auth){
        Utilisateur user =null ;
        if (auth!=null){
            user = userRepository.findByEmail(auth.getName());
        }
        model.addAttribute("user", user);

        List<Utilisateur> users = userRepository.findByStatus(false);
        model.addAttribute("UsersList", users);
        List<Demande> demandes = demandeRepository.findByPub(false);
        model.addAttribute("ReqsList", demandes);
        List<Utilisateur> Utilisateurs = userRepository.findByStatus(true);
        model.addAttribute("UsersListe", Utilisateurs);
        return "admin";
    }

    @GetMapping("/addUser")
    public String addUser(Model model, @RequestParam(name = "id") Long userId) {
        Optional<Utilisateur> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Utilisateur user = optionalUser.get();
            user.setStatus(true);
            userRepository.save(user);
            roleServ.addRoleToUser(user.getEmail(), "USER");

        }
        return "redirect:/adminSpace";
    }

    @GetMapping(path = "/addReq")
    public String addReq(Model model,@RequestParam (name = "id") Long requestId) {
        Optional<Demande> optionalReq = demandeRepository.findById(requestId);
        if (optionalReq.isPresent()) {
            Demande demande = optionalReq.get();
            demande.setPub(true);
            demandeRepository.save(demande);
        }
        return "redirect:/adminSpace";
    }

    @GetMapping (path = "/deleteUser")
    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "redirect:/adminSpace";
    }

    @GetMapping(path = "/deleteReq")
    public String deleteReq(Long id) {
        demandeRepository.deleteById(id);
        return "redirect:/adminSpace";
    }


    /*@GetMapping (path = "/addUser")
    public String addUser(Model model, Utilisateur user) {
        userRepository.findByStatus(false);
            user.setStatus(true);
            userRepository.save(user);
        return "redirect:/adminSpace";

    }*/
}
