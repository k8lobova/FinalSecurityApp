package ru.alishev.springcourse.FirstSecurityApp.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import ru.alishev.springcourse.FirstSecurityApp.models.Theme;
import ru.alishev.springcourse.FirstSecurityApp.security.PersonDetails;
import ru.alishev.springcourse.FirstSecurityApp.services.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static ru.alishev.springcourse.FirstSecurityApp.controllers.Constant.PAGE_SIZE;


/**
 * Controller for POJO {@link ru.alishev.springcourse.FirstSecurityApp.models.Theme;}
 * <p>
 * Это контроллер работает с формой(JSP) создания новой темы, добавляет новую тему и переадресовывает
 * пользователя на главную страницу.
 */

@Controller
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @RequestMapping(value = {"/forum/{id}"}, method = RequestMethod.GET)
    public String forum(Model model, @PathVariable("id") int id) {
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        Pageable pageable = PageRequest.of(id, PAGE_SIZE);
        Page<Theme> allInstanceTheme = themeService.findAllOrderByLastPostDateDesc(pageable);

        model.addAttribute("userRole", userRole);
        model.addAttribute("sizePage", allInstanceTheme.getTotalPages());
        model.addAttribute("allInstanceTheme", allInstanceTheme.getContent());
        model.addAttribute("forumId", id);
        return "forum";
    }

    @RequestMapping(value = "/createTheme", method = RequestMethod.GET)
    public String pageCreateTheme(Model model) {
        model.addAttribute("themeForm", new Theme());
        return "createUpdateTheme";
    }

    @RequestMapping(value = "/createTheme", method = RequestMethod.POST)
    public String addTheme(@ModelAttribute("themeForm") Theme themeForm) {
        if (themeForm.getId() == null) {
            themeForm.setLastPostDate(new Date());
            themeService.save(themeForm);
        }
        return "redirect:/forum/0";
    }

    @RequestMapping(value = "/deleteTheme{id}/", method = RequestMethod.GET)
    public String deleteTheme(@PathVariable("id") int id) {
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (userRole.equals("[ROLE_ADMIN]")) {
            themeService.delete(id);
        }
        return "redirect:/forum/0";
    }

    @RequestMapping(value = "/updateTheme{id}/", method = RequestMethod.GET)
    public String updateTheme(@PathVariable("id") int id, Model model) {
        model.addAttribute("themeForm", themeService.findOne(id));
        List<Theme> allInstanceTheme = themeService.getAllThemes();
        model.addAttribute("allInstanceTheme", allInstanceTheme);
        return "createUpdateTheme";
    }

    @RequestMapping(value = "/updateTheme{id}/", method = RequestMethod.POST)
    public String updateTheme(@PathVariable("id") int id,
                              @ModelAttribute("themeForm") Theme themeForm) {
        themeForm.setLastPostDate(themeService.findOne(id).getLastPostDate());
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (userRole.equals("[ROLE_ADMIN]")) {
            themeService.save(themeForm);
        }
        return "redirect:/forum/0";
    }
}
