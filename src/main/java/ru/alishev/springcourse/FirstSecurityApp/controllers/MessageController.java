package ru.alishev.springcourse.FirstSecurityApp.controllers;

import ru.alishev.springcourse.FirstSecurityApp.models.Theme;
import ru.alishev.springcourse.FirstSecurityApp.models.Message;
import ru.alishev.springcourse.FirstSecurityApp.models.Topic;
import ru.alishev.springcourse.FirstSecurityApp.services.MessageService;
import ru.alishev.springcourse.FirstSecurityApp.services.ThemeService;
import ru.alishev.springcourse.FirstSecurityApp.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    private final MessageService messageService;

    private final TopicService topicService;

    private final ThemeService themeService;

    @Autowired
    public MessageController(MessageService messageService, TopicService topicService, ThemeService themeService) {
        this.messageService = messageService;
        this.topicService = topicService;
        this.themeService = themeService;
    }

    private int id;
    private Date date;

    @RequestMapping(value = "message/{id}", method = RequestMethod.GET)
    public String welcome(@PathVariable("id") int id, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<Message> allInstanceMessages = messageService.getAllMessages();
        Collections.reverse(allInstanceMessages);//что-бы новые сообщения были вверху страницы
        model.addAttribute("userRole", userRole);
        model.addAttribute("username", username);
        model.addAttribute("allInstanceMessages", allInstanceMessages);
        model.addAttribute("topicForm", topicService.findOne(id));
        model.addAttribute("messageForm", new Message());//отправляем в конструктор
        this.id = id;
        return "message";
    }

    /*Тут мы принимаем наш атрибут, который ищется по имени messageForm и хранит в себе инстанс Message,
     * проверка на null (Long obj)
     * добавляем имя юзера
     * добавляем дату и сохраняем в бд*/
    @RequestMapping(value = "message", method = RequestMethod.POST)
    public String addMessage(@ModelAttribute("messageForm") Message messageForm, Model model) {
        if (messageForm.getId() == 0) {
            //if(messageForm.getId() == null){  это я исправила
            messageForm.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            date = new Date();
            messageForm.setDate(date);
            //обновим даты, ибо появилось новое сообщение
            updateDataPost();
            messageForm.setTopicId(id);
            messageService.save(messageForm);
        }
        return "redirect:/message/" + id;
    }

    //БЛОК УДАЛЕНИЯ
    @RequestMapping(value = "/deleteMessage/{id}", method = RequestMethod.GET)
    public String deleteMessage(@PathVariable("id") int id, Model model) {
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (messageService.findOne(id).getUsername().equals
                (SecurityContextHolder.getContext().getAuthentication().getName())
                || userRole.equals("[ROLE_ADMIN]")) {
            messageService.delete(id);
        }
        return "redirect:/message/" + this.id;
    }

    //Этот метод нужен, что-бы обновить данные в нашей БД, а именно время посл. сообщения
    private void updateDataPost() {
        Topic topic = new Topic();
        Theme theme = new Theme();

        topic.setId(topicService.findOne(id).getId());
        topic.setThemeId(topicService.findOne(id).getThemeId());
        topic.setUsername(topicService.findOne(id).getUsername());
        topic.setDescription(topicService.findOne(id).getDescription());
        topic.setLastPostDate(date);
        topic.setTopicName(topicService.findOne(id).getTopicName());
        topicService.save(topic);

        theme.setId(themeService.findById(topicService.findOne(id).getThemeId()).getId());
        theme.setDescription(themeService.findById(topicService.findOne(id).getThemeId()).getDescription());
        theme.setLastPostDate(date);
        theme.setThemeName(themeService.findById(topicService.findOne(id).getThemeId()).getThemeName());
        themeService.save(theme);
    }
}
