package com.example.itutor.controller;

import com.example.itutor.domain.Expert;
import com.example.itutor.domain.Role;
import com.example.itutor.domain.User;
import com.example.itutor.service.EmailSenderService;
import com.example.itutor.service.RoleServiceI;
import com.example.itutor.service.UserServiceI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExpertController {

    private final UserServiceI userService;

    private final RoleServiceI roleService;

    private final PasswordEncoder passwordEncoder;

    private final EmailSenderService emailService;

    private final Validator validator;

    public ExpertController(UserServiceI userService, RoleServiceI roleService, PasswordEncoder passwordEncoder, EmailSenderService emailService, Validator validator) {
        super();
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.validator = validator;
    }

    @RequestMapping(value = "/experts/signup", method = RequestMethod.GET)
    public String showExpertSignup(HttpServletRequest request, Model model) {

        Expert expert = new Expert();
        model.addAttribute("user", expert);

        model.addAttribute("link", "/experts/add/process");

        return "experts/expertSignup";
    }

    @RequestMapping(value = "/experts/add/process")
    public String addExpert(@ModelAttribute @Valid Expert expertRequest,
                            BindingResult result,
                            RedirectAttributes attr,
                            Model model) {

        if (result.hasErrors()) {
            System.out.println("Validation error for created expert: " + result.getAllErrors());
            attr.addFlashAttribute("error", "Some input fields where invalid!");
            return "redirect:/experts/signup";
        }

        // Encode the password before saving
        expertRequest.setPassword(passwordEncoder.encode(expertRequest.getPassword()));

        //Get and set roles
        Role expertRole = roleService.findOrCreate("EXPERT");
        expertRequest.addRole(expertRole);

        // Save the expert and get the created entity
        User createdExpert = null;
        try {
            createdExpert = userService.saveUser(expertRequest);
        }
        catch (Exception e) {
            System.out.println("Error creating expert: " + e.getMessage());
            //render same page with model attribute error
            attr.addFlashAttribute("error", "User with username " + expertRequest.getUsername() + " already exists!");
            return "redirect:/experts/signup";
        }
        if (createdExpert == null) {
            attr.addAttribute("Something went wrong! Please try again!");
            return "redirect:/experts/signup";
        }

        emailService.sendSignupVerificationEmail(createdExpert.getEmail());
        System.out.println("Saved Expert:" + createdExpert);

        attr.addFlashAttribute("success", "Expert added!");
        return "redirect:/";
    }

    @RequestMapping(value = "/experts/edit", method = RequestMethod.GET)
    public String editExpertForm(@RequestParam String userName, Model model, RedirectAttributes attr) {
        //get user by username
        Expert expert = (Expert) userService.findByUsername(userName);

        if (expert == null) {
            // if user was not found
            System.out.println("Expert with username " + userName + " not found");
            attr.addFlashAttribute("error", "Expert not found!");
            return "redirect:/";
        }

        // Set the password to null, so it doesn't get overwritten
        expert.setPassword(null);

        model.addAttribute("user", expert);
        model.addAttribute("link", "/experts/edit/process");

        return "experts/editExpert";
    }

    @RequestMapping(value = "/experts/edit/process")
    public String editExpert(@ModelAttribute
                             Expert expertRequest,
                             BindingResult result,
                             RedirectAttributes attr,
                             Model model) {
        expertRequest.setId(userService.findByUsername(expertRequest.getUsername()).getId());

        // Check if a new password has been entered
        if (!expertRequest.getPassword().isEmpty()) {
            // Encrypt the new password
            String encodedPassword = passwordEncoder.encode(expertRequest.getPassword());
            expertRequest.setPassword(encodedPassword);
        } else {
            // Retrieve the current password from the database and set it, so it doesn't get overwritten
            Expert existingExpert = (Expert) userService.getUserById(expertRequest.getId());
            expertRequest.setPassword(existingExpert.getPassword());
        }

        // Manually invoke validation
        validator.validate(expertRequest, result);

        // Check if there are any validation errors
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            attr.addFlashAttribute("error", "Some input fields where invalid!");
            return "redirect:/";
        }

        // update the expert
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
