package ru.alishev.springcourse.FirstSecurityApp.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstSecurityApp.models.Theme;
import ru.alishev.springcourse.FirstSecurityApp.services.ThemeService;
import ru.alishev.springcourse.FirstSecurityApp.services.PersonDetailsService;

import static ru.alishev.springcourse.FirstSecurityApp.controllers.Constant.PAGE_SIZE;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@SuppressWarnings("SpringMVCViewInspection")
@Controller
public class ThemeController {
    private final PersonDetailsService peopleService;
    private final ThemeService themeService;

    public ThemeController(PersonDetailsService peopleService, ThemeService themeService) {
        this.peopleService = peopleService;
        this.themeService = themeService;
    }

    @RequestMapping(value = {"/forum/{id}"}, method = RequestMethod.GET)
    public String forum(Model model, @PathVariable("id") int id) {
        String userRole = SecurityContextHolder.getContext ().getAuthentication ().getAuthorities ().toString ();
        Pageable pageable = new PageRequest(id, PAGE_SIZE);
        Page allInstanceTheme = themeService.findAll (pageable);

        model.addAttribute ("userRole", userRole);
        model.addAttribute ("sizePage", allInstanceTheme.getTotalPages ());
        model.addAttribute ("allInstanceTheme", allInstanceTheme.getContent ());
        model.addAttribute ("forumId", id);
        return "forum";
    }

    @RequestMapping(value = "/createTheme", method = RequestMethod.GET)
    public String pageCreateTheme(Model model) {
        model.addAttribute ("themeForm", new Theme ());
        return "createUpdateTheme";
    }

    @RequestMapping(value = "/createTheme", method = RequestMethod.POST)
    public String addTheme(@ModelAttribute("themeForm") Theme themeForm) {
        if (themeForm.getId () == 0) {
            themeForm.setLastPostDate (new Date ());
            themeService.save (themeForm);
        }
        return "redirect:/forum";
    }

//    @GetMapping("/new")
//    public String addTheme(@ModelAttribute("theme") Theme theme) {
//        return "theme/new";
//    }
//
//    @PostMapping()
//    public String createTheme(@ModelAttribute("theme") @Valid Theme theme,
//                             BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) return "theme/new";
//        // Устанавливаем lastPostDate в текущее время
//        theme.setLastPostDate(new Date());
//
//        // Сохраняем объект Theme
//        themeService.save(theme);
//        return "redirect:/forum";
//    }




}