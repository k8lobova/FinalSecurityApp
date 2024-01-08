package ru.alishev.springcourse.FirstSecurityApp.controllers;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstSecurityApp.models.Theme;
import ru.alishev.springcourse.FirstSecurityApp.models.Topic;
import ru.alishev.springcourse.FirstSecurityApp.services.MessageService;
import ru.alishev.springcourse.FirstSecurityApp.services.ThemeService;
import ru.alishev.springcourse.FirstSecurityApp.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static ru.alishev.springcourse.FirstSecurityApp.controllers.Constant.PAGE_SIZE;


/**
 * Controller for POJO {@link ru.alishev.springcourse.FirstSecurityApp.models.Theme;}
 * Это контроллер работает с формой(JSP) создания новой темы, добавляет новый топик и переадресовывает
 * пользователя на страницу содержащую список топиков.
 */

@Controller
public class TopicController {

    private final TopicService topicService;

    private final ThemeService themeService;
    private final MessageService messageService;
    private String topicName = "";

    @Autowired
    public TopicController(TopicService topicService, ThemeService themeService, MessageService messageService) {
        this.topicService = topicService;
        this.themeService = themeService;
        this.messageService = messageService;
    }

    //ГЛАВНАЯ
    @RequestMapping(value = "forum/theme/{id}/{idPage}", method = RequestMethod.GET)
    public String topicPage(Model model, HttpServletRequest request,
                            @PathVariable("id") int themeId,
                            @PathVariable("idPage") int idPage,
                            @RequestParam(name = "sort", defaultValue = "asc") String sort) {
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //Pageable pageable = PageRequest.of(idPage, PAGE_SIZE);
        //Pageable pageable = new PageRequest (idPage, PAGE_SIZE);
        Pageable pageable = PageRequest.of(idPage-1, PAGE_SIZE, sort.equalsIgnoreCase("desc") ? Sort.by("lastPostDate").ascending() : Sort.by("lastPostDate").descending());

        Theme theme = themeService.findById(themeId);
        //Page  = topicService.findAllTopicsByThemeId(pageable, theme.getId());
        Page<Topic> allInstanceTopic = topicService.searchByTopicName(pageable, topicName);

        model.addAttribute("sizePage", allInstanceTopic.getTotalPages());
        model.addAttribute("userRole", userRole);
        model.addAttribute("username", username);
        model.addAttribute("allInstanceTopic", allInstanceTopic.getContent());
        model.addAttribute("totalTopicCount", allInstanceTopic.getTotalElements());
        model.addAttribute("themeForm", theme);//для названия в заголовке
        model.addAttribute("themeId", themeId);//для паджинации
        model.addAttribute("idPage", idPage);//для паджинации
        model.addAttribute("sort", sort);
        return "topic";
    }

    //БЛОК СОЗДАНИЯ НОВОГО ТОПИКА
    private int id;

    @RequestMapping(value = "/createTopic", method = RequestMethod.GET)
    public String pageCreateTopic(Model model, HttpServletRequest request) {
        model.addAttribute("topicForm", new Topic());
        id = postURL(request);
        return "createUpdateTopic";
    }

    @RequestMapping(value = "/createTopic", method = RequestMethod.POST)
    public String addTopic(@ModelAttribute("topicForm") Topic topicForm) {
        if (topicForm.getId() == 0) {
            //if (topicForm.getId ( ) == null) {
            topicForm.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            topicForm.setThemeId(id);
            topicForm.setLastPostDate(new Date());
            topicService.save(topicForm);
        }
        return "redirect:/forum/theme/" + id + "/1";
    }

    //БЛОК УДАЛЕНИЯ
    @RequestMapping(value = "/deleteTopic/{id}", method = RequestMethod.GET)
    public String deleteTopic(@PathVariable("id") int id, HttpServletRequest request) {
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (topicService.findById(id).getUsername().equals(
                SecurityContextHolder.getContext().getAuthentication().getName())
                || userRole.equals("[ROLE_ADMIN]")) {
            topicService.delete(id);
            if (messageService.findMessageByTopicId(id) != null)
                messageService.delete(messageService.findMessageByTopicId(id).getId());
        }
        return "redirect:/forum/theme/" + postURL(request) + "/1";
    }

    //БЛОК РЕДАКТИРОВАНИЯ
    private int url;

    @RequestMapping(value = "/updateTopic/{id}", method = RequestMethod.GET)
    public String updateTopic(@PathVariable("id") int id, Model model, HttpServletRequest request) {
        model.addAttribute("topicForm", topicService.findById(id));
        List<Topic> allInstanceTopic = topicService.getAllTopic();
        model.addAttribute("allInstanceTopic", allInstanceTopic);
        url = postURL(request);
        return "createUpdateTopic";
    }

    @RequestMapping(value = "/updateTopic/{id}", method = RequestMethod.POST)
    public String updateTopic(@PathVariable("id") int id,
                              @ModelAttribute("topicForm") Topic topicForm) {
        topicForm.setLastPostDate(topicService.findById(id).getLastPostDate());
        topicForm.setUsername(topicService.findById(id).getUsername());
        topicForm.setThemeId(topicService.findById(id).getThemeId());
        topicService.save(topicForm);

        return "redirect:/forum/theme/" + url + "/1";
    }


    @PostMapping("/forum/theme/{id}/searchTopic")
    public String searchTopic(@PathVariable("id") int id, @ModelAttribute("topicName") String topicName) {
        this.topicName = topicName;
        return "redirect:/forum/theme/" + id + "/1";
    }


    private int postURL(HttpServletRequest request) {
        String url = request.getHeader("referer"); //URL предыдущая страница
        url = url.split("/forum/theme/")[1];
        return Integer.parseInt(url.split("/")[0]);
    }

    private int thisURL(HttpServletRequest request) {
        String url = request.getRequestURI();//URL текущая страница
        url = url.split("/forum/theme/")[1];
        return Integer.parseInt(url.split("/")[0]);
    }
}