package ru.alishev.springcourse.FirstSecurityApp.controllers;

import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @Autowired
    public TopicController(TopicService topicService, ThemeService themeService, MessageService messageService) {
        this.topicService = topicService;
        this.themeService = themeService;
        this.messageService = messageService;
    }

    //ГЛАВНАЯ
    @RequestMapping(value = "topic{id}/{idPage}", method = RequestMethod.GET)
    public String topicPage(Model model, HttpServletRequest request,
                            @PathVariable("id") int topicId,
                            @PathVariable("idPage") int idPage) {
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //Pageable pageable = PageRequest.of(idPage, PAGE_SIZE);
        //Pageable pageable = new PageRequest (idPage, PAGE_SIZE);
        Pageable pageable = PageRequest.of(id, PAGE_SIZE, Sort.by("lastPostDate").descending());
        Theme theme = themeService.findById(thisURL(request));
        Page allInstanceTopic = topicService.findAllTopicsByThemeId(pageable, theme.getId());
        model.addAttribute("sizePage", allInstanceTopic.getTotalPages());
        model.addAttribute("userRole", userRole);
        model.addAttribute("username", username);
        model.addAttribute("allInstanceTopic", allInstanceTopic.getContent());
        model.addAttribute("themeForm", theme);//для названия в заголовке
        model.addAttribute("topicId", topicId);//для паджинации
        model.addAttribute("idPage", idPage);//для паджинации
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
        return "redirect:/topic" + id + "/0";
    }

    //БЛОК УДАЛЕНИЯ
    @RequestMapping(value = "/deleteTopic/{id}", method = RequestMethod.GET)
    public String deleteTopic(@PathVariable("id") int id, HttpServletRequest request) {
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (topicService.findById(id).getUsername().equals(
                SecurityContextHolder.getContext().getAuthentication().getName())
                || userRole.equals("[ROLE_ADMIN]")) {
            topicService.delete(id);
            //messageService.delete(messageService.findMessageByTopicId(id).getId());
        }
        return "redirect:/topic" + postURL(request) + "/0";
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

        return "redirect:/topic" + url + "/0";
    }

    private int postURL(HttpServletRequest request) {
        String url = request.getHeader("referer"); //URL предыдущая страница
        url = url.split("topic")[1];
        return Integer.parseInt(url.split("/")[0]);
    }

    private int thisURL(HttpServletRequest request) {
        String url = request.getRequestURI();//URL текущая страница
        url = url.split("topic")[1];
        return Integer.parseInt(url.split("/")[0]);
    }
}