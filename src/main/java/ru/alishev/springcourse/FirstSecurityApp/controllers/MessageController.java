package ru.alishev.springcourse.FirstSecurityApp.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

import javax.validation.Valid;
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

    @GetMapping("forum/theme/topic/{id}")
    public String welcome(@PathVariable("id") int id, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<Message> allInstanceMessages = messageService.getAllMessages();
        Collections.reverse(allInstanceMessages);//что-бы новые сообщения были вверху страницы
        model.addAttribute("userRole", userRole);
        model.addAttribute("username", username);
        model.addAttribute("allInstanceMessages", allInstanceMessages);
        model.addAttribute("topicForm", topicService.findById(id));
        model.addAttribute("messageForm", new Message());//отправляем в конструктор
        this.id = id;
        return "message";
    }

    /*Тут мы принимаем наш атрибут, который ищется по имени messageForm и хранит в себе инстанс Message,
     * проверка на null (Long obj)
     * добавляем имя юзера
     * добавляем дату и сохраняем в бд*/
    @PostMapping("/forum/theme/topic")
    public String addMessage(@ModelAttribute("messageForm") @Valid Message messageForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            messageForm.setTopicId(id);
            return "message";
        }
        if (messageForm.getId() == 0) {
            messageForm.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            date = new Date();
            messageForm.setDate(date);
            updateDataPost();
            messageForm.setTopicId(id);
            messageService.save(messageForm);
        }
        return "redirect:/forum/theme/topic/" + id;
    }

    //БЛОК УДАЛЕНИЯ
    @GetMapping("/deleteMessage/{id}")
    public String deleteMessage(@PathVariable("id") int id, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (messageService.findById(id).getUsername().equals
                (username) || userRole.equals("[ROLE_ADMIN]")) {
            messageService.delete(id);
        }
        return "redirect:/forum/theme/topic/" + this.id;
    }

    //Этот метод нужен, что-бы обновить данные в нашей БД, а именно время посл. сообщения
    private void updateDataPost() {
        Topic topic = new Topic();
        Theme theme = new Theme();

        topic.setId(topicService.findById(id).getId());
        topic.setThemeId(topicService.findById(id).getThemeId());
        topic.setUsername(topicService.findById(id).getUsername());
        topic.setDescription(topicService.findById(id).getDescription());
        topic.setLastPostDate(date);
        topic.setTopicName(topicService.findById(id).getTopicName());
        topicService.save(topic);

        theme.setId(themeService.findById(topicService.findById(id).getThemeId()).getId());
        theme.setDescription(themeService.findById(topicService.findById(id).getThemeId()).getDescription());
        theme.setLastPostDate(date);
        theme.setThemeName(themeService.findById(topicService.findById(id).getThemeId()).getThemeName());
        themeService.save(theme);
    }
}
