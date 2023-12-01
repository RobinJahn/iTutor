package com.example.itutor.controller;

import com.example.itutor.domain.Researcher;
import com.example.itutor.domain.User;
import com.example.itutor.service.UserServiceI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ResearcherController {

    private UserServiceI userService;

    public ResearcherController(UserServiceI userService) {
        super(); //???
        this.userService = userService;
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

        User createdResearcher = userService.saveUser(researcherRequest);
        System.out.println(createdResearcher);

        attr.addFlashAttribute("success", "Researcher added!");
        return "redirect:/";
    }

    @RequestMapping(value = "/researchers/edit/{researcherId}", method = RequestMethod.GET)
    public String editResearcherForm(@PathVariable Long researcherId, Model model) {
        Researcher researcher = (Researcher) userService.getUserById(researcherId);

        if (researcher == null) {
            // if student was not found -> redirect to another page
            return "errorPage";
        }

        model.addAttribute("user", researcher);
        model.addAttribute("link", "/researchers/edit/process");
        return "researchers/editResearcher";
    }

    @RequestMapping(value = "/researchers/edit/process", method = RequestMethod.POST)
    public String editResearcher(@ModelAttribute @Valid Researcher researcherRequest,
                                 BindingResult result,
                                 RedirectAttributes attr) {

        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            System.out.println(result.getAllErrors());
            return "researchers/editResearcher"; // if there are any errors -> back to edit form
        }

        //TODO: Update the student details in the database based on the edited data in studentRequest

        attr.addFlashAttribute("success", "Researcher updated!");
        return "redirect:/";
    }

}
