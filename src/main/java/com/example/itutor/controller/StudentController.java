package com.example.itutor.controller;

import com.example.itutor.domain.Role;
import com.example.itutor.domain.Student;
import com.example.itutor.domain.User;
import com.example.itutor.service.EmailSenderService;
import com.example.itutor.service.RoleServiceI;
import com.example.itutor.service.UserServiceI;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class StudentController {

    private UserServiceI userService;

    private RoleServiceI roleService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    EmailSenderService emailService;

    private final Validator validator;

    public StudentController(UserServiceI userService, RoleServiceI roleService, Validator validator) {
        super(); //???
        this.userService = userService;
        this.roleService = roleService;
        this.validator = validator;
    }

    @RequestMapping(value = "/students/signup", method = RequestMethod.GET) //http://localhost:8080/students/signup
    public String showStudentSignup(HttpServletRequest request, Model model) {

        Student student = new Student();
        model.addAttribute("user", student);

        model.addAttribute("link", "/students/add/process");

        return "students/studentSignup";
    }


    @RequestMapping(value = "/students/add/process")
    public String addStudent(@ModelAttribute @Valid Student studentRequest,
                             BindingResult result,
                             RedirectAttributes attr){

        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            System.out.println(result.getAllErrors());
            return "signup";
        }

        // Encode the password before saving
        studentRequest.setPassword(passwordEncoder.encode(studentRequest.getPassword()));

        //Get and set roles
        Role role = roleService.findOrCreate("STUDENT");
        studentRequest.addRole(role);

        User createdStudent = null;
        try {
            // Save the student using the service
            createdStudent = userService.saveUser(studentRequest);
        }
        catch (Exception e) {
            System.out.println("Error creating student: " + e.getMessage());
        }
        if (createdStudent == null) {
            //render same page with model attribute error
            attr.addFlashAttribute("error", "User with username " + studentRequest.getUsername() + " already exists!");
            return "redirect:/students/signup";
        }

        emailService.sendSignupVerificationEmail(createdStudent.getEmail());
        System.out.println("Saved Student:" + createdStudent);

        attr.addFlashAttribute("success", "Student added!");
        return "redirect:/";
    }

    //add edit student method that has query parameter id
    // edit field, check if correct, delete button

    @RequestMapping(value = "/students/edit", method = RequestMethod.GET)
    public String editStudentForm(@RequestParam String userName, Model model, RedirectAttributes attr) {
        // get student by username
        Student student = (Student) userService.findByUsername(userName);

        if (student == null) {
            System.out.println("Student with username " + userName + " not found");
            attr.addFlashAttribute("error", "Student not found!");
            return "redirect:/";
        }

        student.setPassword(null);

        // Add the student to the model to pre-populate the form on the page
        model.addAttribute("user", student);
        model.addAttribute("link", "/students/edit/process");
        return "/students/editStudent";
    }

    @RequestMapping(value = "/students/edit/process", method = RequestMethod.POST)
    public String editStudent(@ModelAttribute Student studentRequest,
                              BindingResult result,
                              RedirectAttributes attr,
                              Model model) {

        studentRequest.setId(userService.findByUsername(studentRequest.getUsername()).getId());

        // Check if a new password has been entered
        if (!studentRequest.getPassword().isEmpty()) {
            // Encrypt the new password
            String encodedPassword = passwordEncoder.encode(studentRequest.getPassword());
            studentRequest.setPassword(encodedPassword);
        } else {
            // Retrieve the current password from the database and set it, so it doesn't get overwritten
            Student existingStudent = (Student) userService.getUserById(studentRequest.getId());
            studentRequest.setPassword(existingStudent.getPassword());
        }

        // Manually invoke validation
        validator.validate(studentRequest, result);

        // Check if there are any validation errors
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            attr.addFlashAttribute("error", "Some input fields where invalid!");
            return "redirect:/";
        }

        // update the student
        User updatedStudent = userService.updateUser(studentRequest);
        System.out.println(updatedStudent);

        attr.addFlashAttribute("success", "Student updated!");
        return "redirect:/";

    }

    @RequestMapping(value = "/students/motivation", method = RequestMethod.GET)
    public String showMotivation(HttpServletRequest request, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        }

        User user = userService.findByUsername(username);

        model.addAttribute("user", user);
        return "students/motivation";
    }
}
