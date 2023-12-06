package myblood.controller;


import myblood.model.Demande;
import myblood.model.Utilisateur;
import myblood.repository.DemandeRepository;
import myblood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.List;

@Controller
public class UserControlleur {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DemandeRepository reqRepository;


    @GetMapping(path = "/")
    public String Users(Model model){
        return "index";
    }

    @GetMapping(path = "/login")
    public String login(Model model){
        return "Login";
    }

    @GetMapping(path = "/logout")
    public String logout(Authentication auth){
        auth.setAuthenticated(false);
        return "index";
    }


    @GetMapping(path = "/home")
    public String UserSession(Model model,Authentication auth){
          Utilisateur user =null ;
        if (auth!=null){
            user = userRepository.findByEmail(auth.getName());
        }
        model.addAttribute("user", user);
        return "home";
    }


    @GetMapping(path = "/requests")
    public String Annonces(Model model,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "15") int size,
                           @RequestParam(name = "Type",defaultValue = "") String Type,Authentication auth){
        Utilisateur user =null ;
        if (auth!=null){
            user = userRepository.findByEmail(auth.getName());
        }
        model.addAttribute("user", user);
        Page<Demande> Req = reqRepository.findByGroupeSanguinContainsAndPub(Type, true, PageRequest.of(page, size));
        model.addAttribute("ReqList", Req.getContent());
        model.addAttribute("pages", new int[Req.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("Type", Type);
        return "Annonces";
    }


    @GetMapping(path="/Sign_Up")
    public String SignUp(Model model){
        model.addAttribute("user",
                new Utilisateur());
        return "Sign_Up";
    }

    @GetMapping(path="/createReq")
    public String createReq(Model model,Authentication auth){
        Utilisateur user =null ;
        if (auth!=null){
            user = userRepository.findByEmail(auth.getName());
        }
        model.addAttribute("user", user);
        model.addAttribute("req",
                new Demande());
        return "CreateReq";
    }

    @PostMapping(path="/save")
    public String save(Model model, Utilisateur user){
        userRepository.save(user);
        return "/index";
    }

    @PostMapping(path="/saveReq")
    public String saveReq(Model model, Demande req, Authentication auth){
        String username = auth.getName();
        Utilisateur user = userRepository.findByEmail(username);
        if (user!=null){
            req.setUser(user);
        }
        reqRepository.save(req);
        return "redirect:/createReq";
    }


    @GetMapping(path="/profile")
    public String profile(Model model, @RequestParam Long id){
        Utilisateur user = userRepository.findById(id).orElse(null);
        if(user == null){throw new RuntimeException("User not found");}
        model.addAttribute("user", user);
        List<String> bloodTypes = Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        List<String> genders = Arrays.asList("Male", "Female");
        model.addAttribute("bloodTypes", bloodTypes);
        model.addAttribute("genders", genders);
        List<Demande> myRequests = reqRepository.findByUserAndPub(user, true);
        model.addAttribute("myRequests", myRequests);
        return "profile";
    }


    @PostMapping(path="/update")
    public String update(Model model,@ModelAttribute Utilisateur user){
        user.setStatus(true);
        userRepository.save(user);
        return "redirect:/profile?id=" + user.getId();
    }

    @GetMapping(path="/deleted")
    public String deletemyAccount(Authentication auth,Model model){
        Utilisateur user =null ;
        if (auth!=null){
            user = userRepository.findByEmail(auth.getName());
        }
        model.addAttribute("user", user);
        userRepository.deleteById(user.getId());
        return "index";
    }

    @GetMapping(path="/myReq")
    public String myReq(Model model, @RequestParam Long id,Authentication auth){
        Utilisateur user =null ;
        if (auth!=null){
            user = userRepository.findByEmail(auth.getName());
        }
        model.addAttribute("user", user);
        List<String> bloodTypes = Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        model.addAttribute("bloodTypes", bloodTypes);
        Demande req = reqRepository.findById(id).orElse(null);
        if(req == null){throw new RuntimeException("Request not found");}
        model.addAttribute("req", req);
        return "UpdateReq";
    }

    @PostMapping(path = "/UpdateReq")
    public String updateReq(Model model,Authentication auth,@ModelAttribute Demande req,@RequestParam(name = "id") Long id){
        Utilisateur user =null ;
        if (auth!=null){
            user = userRepository.findByEmail(auth.getName());
        }
        model.addAttribute("user", user);

         req = reqRepository.findById(id).orElse(null);
        if (req != null && req.getUser() != null) {
            Long userId = req.getUser().getId();
            req.setPub(true);
            reqRepository.save(req);
            return "redirect:/profile?id=" + userId;
        } else {
            return "redirect:/profile";
        }

    }

    @GetMapping(path = "/ReqDeleted")
    public String deleteReq(@RequestParam("id") Long id, Model model,Authentication auth){
        Utilisateur user =null ;
        if (auth!=null){
            user = userRepository.findByEmail(auth.getName());
        }
        model.addAttribute("user", user);
        Demande req = reqRepository.findById(id).orElse(null);
        if (req != null && req.getUser() != null) {
            Long userId = req.getUser().getId();
            reqRepository.deleteById(id);
            return "redirect:/profile?id=" + userId;
        } else {
            return "redirect:/profile";
        }
       // reqRepository.deleteById(id);
       // return "redirect:/profile?id=" + req.getUser().getId();
    }


    @GetMapping(path = "/docs")
    public String arti(Model model){
        return "arti";
    }

    @GetMapping(path = "/articles")
    public String articles(Model model,Authentication auth){
        Utilisateur user =null ;
        if (auth!=null){
            user = userRepository.findByEmail(auth.getName());
        }
        model.addAttribute("user", user);
        return "articles";
    }
    @GetMapping(path = "/BloodTypes")
    public String Article1(Model model,Authentication auth){
        Utilisateur user =null ;
        if (auth!=null){
            user = userRepository.findByEmail(auth.getName());
        }
        model.addAttribute("user", user);
        return "Blood_Types";
    }
    @GetMapping(path = "/DonationsBenefits")
    public String Article2(Model model,Authentication auth){
        Utilisateur user =null ;
        if (auth!=null){
            user = userRepository.findByEmail(auth.getName());
        }
        model.addAttribute("user", user);
        return "DonationsBenefits";
    }
    @GetMapping(path = "/Advantages_Disadvantages")
    public String Article3(Model model,Authentication auth){
        Utilisateur user =null ;
        if (auth!=null){
            user = userRepository.findByEmail(auth.getName());
        }
        model.addAttribute("user", user);
        return "law";
    }
    @GetMapping(path = "/howtodonate")
    public String Article4(Model model,Authentication auth){
        Utilisateur user =null ;
        if (auth!=null){
            user = userRepository.findByEmail(auth.getName());
        }
        model.addAttribute("user", user);
        return "how";
    }

        /*    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }*/

   /* @PostMapping(path="/save")
    public String save2(@ModelAttribute("user") Utilisateur user, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setCin(fileName);
            Utilisateur save2 = utilisateurRepository.save(user);
            String upload = "images/" + user.getId();

            saveFile(upload, fileName, multipartFile);
        }else{
            if(user.getCin().isEmpty()){
                user.setCin(null);
                utilisateurRepository.save(user);
            }
        }
        utilisateurRepository.save(user);
        return "/index";
    }*/
}
