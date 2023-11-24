package com.example.itutor.controller;

import com.example.itutor.domain.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {

    @RequestMapping(value = "/students/login", method = RequestMethod.GET) //http://localhost:8080/students/login
    public String showStudentLogin(HttpServletRequest request, Model model) {

        Student student = new Student();
        student.setId((long) -1);
        LocalDate date= LocalDate.now();
        student.setBirthDate(date);
        model.addAttribute("student", student);

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

        //TODO: save student to database

        attr.addFlashAttribute("success", "Student added!");
        return "redirect:/home";
    }

    //add edit student method that has query parameter id
    // edit field, check if correct, delete button

}
