package com.edu.nus.iss.miniproject.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.nus.iss.miniproject.model.NewUser;
import com.edu.nus.iss.miniproject.model.User;
import com.edu.nus.iss.miniproject.model.Word;
import com.edu.nus.iss.miniproject.repository.SearchRepository;
import com.edu.nus.iss.miniproject.repository.UserRepository;
import com.edu.nus.iss.miniproject.service.SearchService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/dictionary")
public class MainController {

    @Autowired
    private SearchService dbSvc;

    @Autowired
    SearchRepository searchRepo;

    @Autowired
    UserRepository userRepo;

    // access registration page
    @GetMapping("/register")
    public String registrationPage (Model model,HttpSession session) {
        
        model.addAttribute("newUser", new NewUser());
        return "register";
    }

    //registration outcome
    @PostMapping("/registered")
    public String registrationSuccess (@Valid @ModelAttribute("newUser") NewUser newUser, BindingResult binding, HttpSession session) {
        if (binding.hasErrors()) {
            return "register";
        } else {
            //userRepo.addUser(newUser);
            if (userRepo.hasUser(newUser.getUsername())){
                 binding.rejectValue("username", "username already exists", "username not available. Please choose a new username");
                return "register";
            } else {
                //System.out.printf("\nusername:%s , password:%s\n",newUser.getUsername(),newUser.getPassword());
               userRepo.addUser(newUser);
                return "registered";
            // } else {
            //     userRepo.addUser(newUser);
            //     return "registered";
            // }
                // FieldError error = new FieldError("form", "username", "username not available. Please choose a new username");
            // return "register";
        }
    }
    }
    
    // access login page
    @GetMapping("/login")
    public String loginPage(Model model,HttpSession session) {
        model.addAttribute("user", new User());

        return "login";

    }

    // access home page
    @PostMapping("/home")
    public String loginSuccess (@Valid @ModelAttribute("user") User user, BindingResult binding, HttpSession session, Model model) {
        if (binding.hasErrors()) {
            return "login";
        } else {
            String currUser = user.getUsername();
            //System.out.printf("username check : %s\n",currUser);
            
            if (userRepo.hasUser(currUser)){
                if (userRepo.checkPassword(currUser, user)){
                //String currUser = user.getUsername();
                session.setAttribute("username", currUser);
                //session.getAttribute("username");
                //String currUser = (String) session.getAttribute("username");
                model.addAttribute("username", currUser);
                //System.out.printf("username check @ home (post): %s\n",session.getAttribute("username"));
                return "home";
                } 
                else {
                     binding.rejectValue("password", "password does not match", "password incorrect. Please try again");
            return "login";
        }
    }
        else {
            binding.rejectValue("username", "username does not match", "username incorrect. Please try again");
            return "login";
        }
    }
    
}
    
    @GetMapping("/home")
        public String getHome(Model model, HttpSession session) {
            model.addAttribute("username", session.getAttribute("username"));
            //model.addAttribute("user", new User());
            
            //String currUser = (String)session.getAttribute("username");
            //System.out.printf("username check @ home (get): %s\n", session.getAttribute("username"));
            return "home";
        }


    // access dictionary function
    @GetMapping("/search")
    public String getSearch(Model model, HttpSession session) {
        model.addAttribute("searchInput", new Word());
        model.addAttribute("username", session.getAttribute("username"));
        //System.out.printf("username check @ search (get): %s\n", session.getAttribute("username"));
        return "search";
    }

    // return word definition
    @PostMapping("/result")
    public String sendSearch (@Valid @ModelAttribute("searchInput") Word searchInput, BindingResult binding, @RequestBody MultiValueMap<String, String> body, @ModelAttribute("newUser") NewUser user, Model model, HttpSession session) {
        if (binding.hasErrors()) {
            
            return "search";

        } 
        else {
            model.addAttribute("username", session.getAttribute("username"));
            //String username = (String)session.getAttribute("username");
            //System.out.println(username);
            String wordInput = body.getFirst("wordValue"); // get input from form
            
            Word definedWord = dbSvc.getWord(wordInput);
            //Word definedWord = dbSvc.getWord(wordInput); // make API call to https://api.api-ninjas.com/v1/dictionary?word=
            searchRepo.saveSearchInput((String)session.getAttribute("username"), definedWord); //save the word into repo (history list)
            if (!definedWord.getDefinition().isEmpty()) {
                model.addAttribute("word", definedWord);
                
                    return "result";
                } 
                else {
                    model.addAttribute("searchInput", wordInput);
                    return "searcherror";
                }
            }
        }

    // access user search history
    
    @GetMapping ("/history/{username}")
    public String getSearchHistory (@PathVariable(name = "username", required = true) String username, Word word, Model model, HttpSession session){
        
        List<Word> searchList = searchRepo.getAllHistory((String)session.getAttribute("username"));
        //System.out.println(searchList);
        model.addAttribute("history", searchList);
        //System.out.printf("username check @ hist (get): %s\n", username);
        model.addAttribute("username", session.getAttribute("username"));
        //model.addAttribute("username", username);
        return "history";
    }

    @GetMapping ("/docs")
    public String getDocs (Model model, HttpSession session){
        model.addAttribute("username", session.getAttribute("username"));
        return "docs";
    }

    // log out and end session
    @GetMapping("/logout")
    public String userLogout (HttpSession session){
        session.invalidate();
        return "logout";
    }

}