package ru.alishev.springcourse.FirstSecurityApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstSecurityApp.models.Theme;
import ru.alishev.springcourse.FirstSecurityApp.services.ThemeService;
import ru.alishev.springcourse.FirstSecurityApp.services.PersonDetailsService;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("SpringMVCViewInspection")
@Controller
@RequestMapping("/theme")
public class ThemeController {
    private final PersonDetailsService peopleService;
    private final ThemeService themeService;

    public ThemeController(PersonDetailsService peopleService, ThemeService themeService) {
        this.peopleService = peopleService;
        this.themeService = themeService;
    }

    @GetMapping("/new")
    public String addTheme(@ModelAttribute("theme") Theme theme) {
        return "theme/new";
    }

    @PostMapping()
    public String createTheme(@ModelAttribute("theme") @Valid Theme theme,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "theme/new";
        themeService.save(theme);
        return "redirect:/theme";
    }




}