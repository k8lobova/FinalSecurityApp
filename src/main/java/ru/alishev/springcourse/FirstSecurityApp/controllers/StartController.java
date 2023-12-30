package ru.alishev.springcourse.FirstSecurityApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Neil Alishev
 */
@Controller
public class StartController {

    @GetMapping("/forum")
    public String forumPage() {
        return "forum";
    }

}
