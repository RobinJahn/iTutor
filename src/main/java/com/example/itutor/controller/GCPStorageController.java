package com.example.itutor.controller;


import com.example.itutor.service.ContentServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/gcp")
public class GCPStorageController {
    private final ContentServiceI contentService;

    @Autowired
    public GCPStorageController(ContentServiceI contentService) {
        this.contentService = contentService;
    }

    @PostMapping("/upload")
    public String handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("contentType") String contentType, Model model) {

        // ContentService Methode to Upload document
        String result = contentService.uploadContentDocument("i-tutor", file, title, contentType);

        // for upload page
        model.addAttribute("documentName", title);
        return "upload";
    }
}
