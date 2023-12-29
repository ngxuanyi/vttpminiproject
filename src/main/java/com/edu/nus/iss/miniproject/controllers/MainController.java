package com.edu.nus.iss.miniproject.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
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
            userRepo.addUser(newUser);
            return "registered";
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
    public String userLogin(@Valid @ModelAttribute("user") User user, BindingResult binding, HttpSession session, @RequestBody MultiValueMap<String,String> loginForm, Model model) {
        if (binding.hasErrors()) {
            return "login";
        } else {
            String username = loginForm.getFirst("username");
            System.out.printf("username check: %s\n", username);
            session.setAttribute("username", username);
            model.addAttribute("username", username);
            return "home";
        }
    }

    // access dictionary function
    @GetMapping("/search")
    public String getSearch() {
        return "search";
    }

    // return word definition
    @PostMapping("/result")
    public String searchForm(@RequestBody MultiValueMap<String, String> body, @ModelAttribute("newUser") NewUser user, Model model, HttpSession session) {

        String username = (String)session.getAttribute("username");
        System.out.println(username);
        String wordInput = body.getFirst("searchInput"); // get input from form

        Word definedWord = dbSvc.getWord(wordInput); // make API call to https://api.api-ninjas.com/v1/dictionary?word=
        
        
        model.addAttribute("word", definedWord);

        searchRepo.saveSearchInput(username, definedWord); //save the word into repo (history list)

        return "result";
    }

    // access user search history
    @GetMapping ("/history/{username}")
    public String searchHistory (@PathVariable(name = "username", required = true) String username, Word word, Model model, HttpSession session){
        // String user = (String)session.getAttribute("username");
        Map<String,String> searchList = searchRepo.getAllHistory(username, word);
        //System.out.println(searchList);
        model.addAttribute("history", searchList);
        System.out.printf("username check: %s\n", username);
        model.addAttribute("username", username);
        return "history";

    }

    // log out and end session
    @GetMapping("/logout")
    public String userLogout (HttpSession session){
        session.invalidate();
        return "logout";
    }

}