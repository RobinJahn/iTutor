package com.example.itutor.controller;


import com.example.itutor.domain.AiMessages;
import com.example.itutor.domain.User;
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

@Controller
public class AIController {

    private final OpenAIServiceI openAIService;

    private final UserServiceI userService;

    private User currentUser;

    public AIController(OpenAIServiceI openAIService, UserServiceI userService) {
        this.openAIService = openAIService;
        this.userService = userService;
    }

    @GetMapping("/ai")
    public String showAI(Model model) {
        //get currently logged-in user with spring boot security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = null;
        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                currentUserName = ((UserDetails) principal).getUsername();
            } else {
                currentUserName = principal.toString();
            }
        }
        if (currentUserName == null) {
            return "redirect:/login";
        }
        currentUser = userService.findByUsername(currentUserName);

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
        // For example, send it to OpenAI service and get a response
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
}
