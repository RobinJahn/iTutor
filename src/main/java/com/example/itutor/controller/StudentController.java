package com.example.itutor.controller;

import com.example.itutor.domain.Role;
import com.example.itutor.domain.Student;
import com.example.itutor.domain.User;
import com.example.itutor.service.EmailSenderService;
import com.example.itutor.service.RoleServiceI;
import com.example.itutor.service.UserServiceI;
import com.example.itutor.service.impl.EmailSenderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    public StudentController(UserServiceI userService, RoleServiceI roleService) {
        super(); //???
        this.userService = userService;
        this.roleService = roleService;
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

        // Save the student using the service
        User createdStudent = userService.saveUser(studentRequest);

        emailService.sendEmail(createdStudent.getEmail());
        System.out.println("Saved Student:" + createdStudent);

        attr.addFlashAttribute("success", "Student added!");
        return "redirect:/";
    }

    //add edit student method that has query parameter id
    // edit field, check if correct, delete button

    @RequestMapping(value = "/students/edit/{studentId}", method = RequestMethod.GET)
    public String editStudentForm(@PathVariable Long studentId, Model model) {
        // Here the logic would have to be implemented to retrieve the student with the given studentId from the database

        // get student from database by id
        Student student = (Student) userService.getUserById(studentId);

        if (student == null) {
            // if student was not found -> redirect to another page
            System.err.println("Student with id " + studentId + " not found!");
            return "errorPage";
        }

        // Add the student to the model to pre-populate the form on the page
        model.addAttribute("user", student);
        model.addAttribute("link", "/students/edit/process");
        return "/students/editStudent";
    }

    @RequestMapping(value = "/students/edit/process", method = RequestMethod.POST)
    public String editStudent(@ModelAttribute @Valid Student studentRequest,
                              BindingResult result,
                              RedirectAttributes attr) {

        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            System.out.println(result.getAllErrors());
            return "/students/editStudent"; // if there are any errors -> back to edit form
        }

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
