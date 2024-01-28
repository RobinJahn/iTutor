package com.example.itutor.controller;


import com.example.itutor.domain.AiMessages;
import com.example.itutor.domain.Course;
import com.example.itutor.domain.User;
import com.example.itutor.service.CourseServiceI;
import com.example.itutor.service.OpenAIServiceI;
import com.example.itutor.service.UserServiceI;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class AIController {

    private final OpenAIServiceI openAIService;

    private final UserServiceI userService;

    private final CourseServiceI courseService;

    private User currentUser;

    public AIController(OpenAIServiceI openAIService, UserServiceI userService, CourseServiceI courseService) {
        this.openAIService = openAIService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("/ai")
    public String showAI(Model model) {
        currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        AiMessages aiMessages = openAIService.getMessagesForUser(currentUser);

        model.addAttribute("aiMessages", aiMessages.messagesWithoutFirst());
        return "/ai";
    }

    @PostMapping("/ai/message")
    public String postMessage(@RequestParam("message") String message, Model model) {

        // Update aiMessages with the new messages
        AiMessages aiMessages = openAIService.getMessagesForUser(currentUser);
        aiMessages.addMessage(message); // User's message

        // Process the message
        String response = openAIService.getChatResponse(aiMessages);


        aiMessages.addMessage(response); // AI's response
        openAIService.saveMessages(aiMessages);

        model.addAttribute("aiMessages", aiMessages.messagesWithoutFirst());
        return "redirect:/ai";
    }

    @GetMapping("/ai/delete")
    public String deleteChat() {
        openAIService.resetMessagesForUser(currentUser);
        return "redirect:/ai"; // Redirect to the chat page
    }


    @GetMapping("/aiTranslate")
    public String showAiTranslate(Model model){
        currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "/aiTranslate";
    }

    @PostMapping("/aiTranslate")
    public String sendRequestAndAssembleMessages(Model model,
                                                 @RequestParam("selectedCourse") Long courseId,
                                                 @RequestParam("targetLanguage") String targetLanguage) {
        Optional<Course> optionalCourse = courseService.getCourseById(courseId);
        if (!optionalCourse.isPresent()) {
            return "redirect:/aiTranslate";
        }
        Course selectedCourse = optionalCourse.get();
        String courseText = selectedCourse.getCourseAsText();


        openAIService.deleteMessagesForUser(currentUser);
        AiMessages aiMessage = new AiMessages(currentUser);
        aiMessage.addMessage("Please translate this course to " + targetLanguage + ": " + courseText);

        // Send the courseText to AI and get the response
        String aiResponse = openAIService.getChatResponse(aiMessage);
        // Handle AI response
        aiMessage.addMessage(aiResponse);
        AiMessages aiMessages = openAIService.saveMessages(aiMessage);
        model.addAttribute("aiMessages", aiMessages.messagesWithoutFirst());

        return "redirect:/ai";
    }

}
