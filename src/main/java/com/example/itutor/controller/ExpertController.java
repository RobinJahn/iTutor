package com.example.itutor.controller;

import com.example.itutor.domain.Expert;
import com.example.itutor.domain.Role;
import com.example.itutor.domain.User;
import com.example.itutor.repository.UserRepositoryI;
import com.example.itutor.repository.impl.UserRepositoryImpl;
import com.example.itutor.service.EmailSenderService;
import com.example.itutor.service.RoleServiceI;
import com.example.itutor.service.UserServiceI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
public class ExpertController {

    private final UserServiceI userService;

    private final RoleServiceI roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    EmailSenderService emailService;

    public ExpertController(UserServiceI userService, RoleServiceI roleService) {
        super(); //???
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/experts/signup", method = RequestMethod.GET) //http://localhost:8080/experts/signup
    public String showExpertSignup(HttpServletRequest request, Model model) {

        Expert expert = new Expert();
        model.addAttribute("user", expert);

        model.addAttribute("link", "/experts/add/process");

        return "experts/expertSignup";
    }

    @RequestMapping(value = "/experts/add/process")
    public String addExpert(@ModelAttribute @Valid Expert expertRequest,
                            BindingResult result,
                            RedirectAttributes attr) {

        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            System.out.println(result.getAllErrors());
            return "signup";
        }

        // Encode the password before saving
        expertRequest.setPassword(passwordEncoder.encode(expertRequest.getPassword()));

        //Get and set roles
        Role expertRole = roleService.findOrCreate("EXPERT");
        expertRequest.addRole(expertRole);

        // Save the expert and get the created entity
        User createdExpert = userService.saveUser(expertRequest);

        emailService.sendEmail(createdExpert.getEmail());
        System.out.println("Saved Expert:" + createdExpert);

        attr.addFlashAttribute("success", "Expert added!");
        return "redirect:/";
    }

    @RequestMapping(value = "/experts/edit/{expertId}", method = RequestMethod.GET)
    public String editExpertForm(@PathVariable Long expertId, Model model) {

        Expert expert = (Expert) userService.getUserById(expertId); //TODO: test if that fails on return null

        if (expert == null) {
            System.err.println("Expert with id " + expertId + " not found!");
            return "errorPage";
        }

        model.addAttribute("user", expert);
        model.addAttribute("link", "/experts/edit/process");

        return "experts/editExpert";
    }

    @RequestMapping(value = "/experts/edit/process")
    public String editExpert(@ModelAttribute
                             @Valid Expert expertRequest,
                             BindingResult result,
                             RedirectAttributes attr) {

        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            System.out.println(result.getAllErrors());
            return "experts/editExpert";
        }

        User updatedExpert = userService.updateUser(expertRequest);
        System.out.println(updatedExpert);

        attr.addFlashAttribute("success", "Expert updated!");
        return "redirect:/";
    }

    @RequestMapping(value = "/experts/guideline", method = RequestMethod.GET)
    public String showGuideline() {
        return "experts/guideline";
    }
}
