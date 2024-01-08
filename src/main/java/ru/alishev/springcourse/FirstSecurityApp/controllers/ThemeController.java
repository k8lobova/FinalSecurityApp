package ru.alishev.springcourse.FirstSecurityApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstSecurityApp.models.Theme;
import ru.alishev.springcourse.FirstSecurityApp.services.MessageService;
import ru.alishev.springcourse.FirstSecurityApp.services.ThemeService;
import ru.alishev.springcourse.FirstSecurityApp.services.PersonDetailsService;
import ru.alishev.springcourse.FirstSecurityApp.services.TopicService;

import static ru.alishev.springcourse.FirstSecurityApp.controllers.Constant.PAGE_SIZE;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@SuppressWarnings("SpringMVCViewInspection")
@Controller
public class ThemeController {
    private final PersonDetailsService peopleService;
    private final ThemeService themeService;
    private final TopicService topicService;

    @Autowired
    public ThemeController(PersonDetailsService peopleService, ThemeService themeService, TopicService topicService) {
        this.peopleService = peopleService;
        this.themeService = themeService;
        this.topicService = topicService;
    }

    @GetMapping("/forum/{id}")
    public String forum(Model model, @PathVariable("id") int id) {
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        //Pageable pageable = new PageRequest(id, PAGE_SIZE);
        Pageable pageable = PageRequest.of(id-1, PAGE_SIZE, Sort.by("lastPostDate").descending());
        //Pageable pageable = PageRequest.of(id, PAGE_SIZE);
        Page allInstanceTheme = themeService.findAll(pageable);

        model.addAttribute("userRole", userRole);
        model.addAttribute("sizePage", allInstanceTheme.getTotalPages());
        model.addAttribute("allInstanceTheme", allInstanceTheme.getContent());
        model.addAttribute("totalThemeCount", allInstanceTheme.getTotalElements());
        model.addAttribute("forumId", id);
        return "forum";
    }

    @GetMapping("/createTheme")
    public String pageCreateTheme(Model model) {
        model.addAttribute("themeForm", new Theme());
        return "createUpdateTheme";
    }

    @PostMapping("/createTheme")
    public String addTheme(@ModelAttribute("themeForm") Theme themeForm) {
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (userRole.equals ("[ROLE_ADMIN]")) {
            if (themeForm.getId() == 0) {
                themeForm.setLastPostDate(new Date());
                themeService.save(themeForm);
            }
        }
        return "redirect:/forum/1";
    }


    //БЛОК УДАЛЕНИЯ
    @GetMapping("/deleteTheme/{id}")
    public String deleteTheme(@PathVariable("id") int id) {
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (userRole.equals("[ROLE_ADMIN]")) {
            themeService.delete(id);
            if (topicService.findTopicsByThemeId(id) != null) {
                topicService.deleteAll(topicService.findTopicsByThemeId(id));
            }
        }
        return "redirect:/forum/1";
    }




    //БЛОК РЕДАКТИРОВАНИЯ
    @GetMapping("/updateTheme/{id}")
    public String updateTheme(@PathVariable("id") int id, Model model) {
        model.addAttribute("themeForm", themeService.findById(id));
        List<Theme> allInstanceTheme = themeService.getAllThemes();
        model.addAttribute("allInstanceTheme", allInstanceTheme);
        return "createUpdateTheme";
    }

    @PostMapping("/updateTheme/{id}")
    public String updateTheme(@PathVariable("id") int id,
                              @ModelAttribute("themeForm") Theme themeForm) {
        themeForm.setLastPostDate(themeService.findById(id).getLastPostDate());
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (userRole.equals("[ROLE_ADMIN]")) {
            themeService.save(themeForm);
        }

        return "redirect:/forum/1";
    }


    @PostMapping("/forum/searchTheme")
    public String searchTheme(Model model, @ModelAttribute("themeName") String themeName){
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        Pageable pageable = PageRequest.of(0, PAGE_SIZE, Sort.by("lastPostDate").descending());

        //Page<Theme> searchResult = themeService.findByThemeNameContaining(pageable,themeName);
        Page<Theme> searchResult = themeService.searchByThemeName(pageable,themeName);

        model.addAttribute("userRole", userRole);
        model.addAttribute("sizePage", searchResult.getTotalPages());
        model.addAttribute("allInstanceTheme", searchResult.getContent());
        model.addAttribute("totalThemeCount", searchResult.getTotalElements());
        model.addAttribute("forumId", 0);
        return "forum";
    }

}