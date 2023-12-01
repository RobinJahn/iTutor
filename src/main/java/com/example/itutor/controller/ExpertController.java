package com.example.itutor.controller;

import com.example.itutor.domain.Expert;
import com.example.itutor.domain.Student;
import com.example.itutor.domain.User;
import com.example.itutor.service.UserServiceI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExpertController {

    private UserServiceI expertService;

    public ExpertController(UserServiceI expertService) {
        super(); //???
        this.expertService = expertService;
    }

    @RequestMapping(value = "/experts/signup", method = RequestMethod.GET) //http://localhost:8080/experts/signup
    public String showExpertSignup(HttpServletRequest request, Model model) {

        Expert expert = new Expert();
        model.addAttribute("user", expert);

        model.addAttribute("link", "/experts/add/process");

        return "signup";
    }

    @RequestMapping(value = "/experts/add/process")
    public String addExpert(@ModelAttribute
                            @Valid Student studentRequest,
                            BindingResult result,
                            RedirectAttributes attr) {

        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            System.out.println(result.getAllErrors());
            return "signup";
        }

        User createdExpert = expertService.saveUser(studentRequest);
        System.out.println(createdExpert);

        attr.addFlashAttribute("success", "Expert added!");
        return "redirect:/home";
    }

    @RequestMapping(value = "/experts/edit/{id}", method = RequestMethod.GET)
    public String showEditExpert(HttpServletRequest request, Model model) {

        Long expertId = Long.parseLong(request.getParameter("id"));
        Expert expert = (Expert) expertService.getUserById(expertId);
        model.addAttribute("user", expert);

        model.addAttribute("link", "/experts/edit/process");

        return "signup";
    }

    @RequestMapping(value = "/experts/edit/process")
    public String editExpert(@ModelAttribute
                             @Valid Expert expertRequest,
                             BindingResult result,
                             RedirectAttributes attr) {

        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            System.out.println(result.getAllErrors());
            return "signup";
        }

        User updatedExpert = expertService.updateUser(expertRequest);
        System.out.println(updatedExpert);

        attr.addFlashAttribute("success", "Expert updated!");
        return "redirect:/home";
    }
}
