package com.example.itutor.controller;

import com.example.itutor.domain.Student;
import com.example.itutor.domain.User;
import com.example.itutor.service.UserServiceI;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {

    private UserServiceI studentService;

    public StudentController(UserServiceI studentService) {
        super(); //???
        this.studentService = studentService;
    }

    @RequestMapping(value = "/students/signup", method = RequestMethod.GET) //http://localhost:8080/students/signup
    public String showStudentSignup(HttpServletRequest request, Model model) {

        Student student = new Student();
        LocalDate date= LocalDate.now();
        student.setBirthDate(date);
        model.addAttribute("user", student);

        model.addAttribute("link", "/students/add/process");

        return "signup";
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

        User createdStudent = studentService.saveUser(studentRequest);
        System.out.println(createdStudent);

        attr.addFlashAttribute("success", "Student added!");
        return "redirect:/home";
    }

    //add edit student method that has query parameter id
    // edit field, check if correct, delete button

    @RequestMapping(value = "/students/edit/{studentId}", method = RequestMethod.GET)
    public String editStudentForm(@PathVariable Long studentId, Model model) {
        // Here the logic would have to be implemented to retrieve the student with the given studentId from the database

        // get student from database by id
        User student = studentService.getUserById(studentId);

        if (student != null) {
            // Add the student to the model to pre-populate the form on the page
            model.addAttribute("student", student);
            return "editStudent";
        } else {
            // if student was not found -> redirect to another page
            return "errorPage";
        }
    }

    @RequestMapping(value = "/students/edit/process", method = RequestMethod.POST)
    public String editStudent(@ModelAttribute @Valid Student studentRequest,
                              BindingResult result,
                              RedirectAttributes attr) {

        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            System.out.println(result.getAllErrors());
            return "editStudent"; // if there are any errors -> back to edit form
        }

        //TODO: Update the student details in the database based on the edited data in studentRequest

        attr.addFlashAttribute("success", "Student updated!");
        return "redirect:/home";

    }
}
