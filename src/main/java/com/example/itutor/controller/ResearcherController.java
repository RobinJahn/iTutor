package com.example.itutor.controller;

import com.example.itutor.domain.Researcher;
import com.example.itutor.domain.Role;
import com.example.itutor.domain.User;
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
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
public class ResearcherController {

    private final UserServiceI userService;

    private final RoleServiceI roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    EmailSenderService emailService;

    private final Validator validator;

    public ResearcherController(UserServiceI userService, RoleServiceI roleService,  Validator validator) {
        super(); //???
        this.userService = userService;
        this.roleService = roleService;
        this.validator = validator;
    }

    @RequestMapping(value = "/researchers/signup", method = RequestMethod.GET)
    public String showResearcherSignup(HttpServletRequest request, Model model) {

        Researcher researcher = new Researcher();
        model.addAttribute("user", researcher);

        model.addAttribute("link", "/researchers/add/process");

        return "researchers/researcherSignup";
    }

    @RequestMapping(value = "/researchers/add/process")
    public String addResearcher(@ModelAttribute @Valid Researcher researcherRequest,
                                BindingResult result,
                                RedirectAttributes attr) {

        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            System.out.println(result.getAllErrors());
            return "signup";
        }

        // Encode the password before saving
        researcherRequest.setPassword(passwordEncoder.encode(researcherRequest.getPassword()));

        //Get and set roles
        Role researcherRole = roleService.findOrCreate("RESEARCHER");
        researcherRequest.addRole(researcherRole);

        // Save the student using the service
        User createdResearcher = null;
        try {
            // Save the student using the service
            createdResearcher = userService.saveUser(researcherRequest);
        }
        catch (Exception e) {
            System.out.println("Error creating student: " + e.getMessage());
        }
        if (createdResearcher == null) {
            //render same page with model attribute error
            attr.addFlashAttribute("error", "User with username " + researcherRequest.getUsername() + " already exists!");
            return "redirect:/students/signup";
        }

        emailService.sendEmail(createdResearcher.getEmail());
        System.out.println("Saved Researcher:" + createdResearcher);


        attr.addFlashAttribute("success", "Researcher added!");
        return "redirect:/";
    }

    @RequestMapping(value = "/researchers/edit", method = RequestMethod.GET)
    public String editResearcherForm(@RequestParam String userName, Model model, RedirectAttributes attr) {
        // get researcher by username
        Researcher researcher = (Researcher) userService.findByUsername(userName);

        if (researcher == null) {
            // if researcher was not found -> redirect to another page
            System.out.println("Researcher with username " + userName + " not found");
            attr.addFlashAttribute("error", "Researcher not found!");
            return "redirect:/";
        }

        researcher.setPassword(null);

        model.addAttribute("user", researcher);
        model.addAttribute("link", "/researchers/edit/process");
        return "researchers/editResearcher";
    }

    @RequestMapping(value = "/researchers/edit/process", method = RequestMethod.POST)
    public String editResearcher(@ModelAttribute Researcher researcherRequest,
                                 BindingResult result,
                                 RedirectAttributes attr,
                                 Model model) {

        researcherRequest.setId(userService.findByUsername(researcherRequest.getUsername()).getId());

        // Check if a new password has been entered
        if (!researcherRequest.getPassword().isEmpty()) {
            // Encrypt the new password
            String encodedPassword = passwordEncoder.encode(researcherRequest.getPassword());
            researcherRequest.setPassword(encodedPassword);
        } else {
            // Retrieve the current password from the database and set it, so it doesn't get overwritten
            Researcher existingResearcher = (Researcher) userService.getUserById(researcherRequest.getId());
            researcherRequest.setPassword(existingResearcher.getPassword());
        }

        // Manually invoke validation
        validator.validate(researcherRequest, result);

        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            System.out.println(result.getAllErrors());
            //add model attribute user
            model.addAttribute("user", researcherRequest);
            return "researchers/editResearcher"; // if there are any errors -> back to edit form
        }



        User updatedResearcher = userService.updateUser(researcherRequest);
        System.out.println(updatedResearcher);

        attr.addFlashAttribute("success", "Researcher updated!");
        return "redirect:/";
    }


}
