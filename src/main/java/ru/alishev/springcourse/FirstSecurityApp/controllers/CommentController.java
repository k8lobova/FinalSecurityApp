package ru.alishev.springcourse.FirstSecurityApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.alishev.springcourse.FirstSecurityApp.models.Comment;
import ru.alishev.springcourse.FirstSecurityApp.models.Message;
import ru.alishev.springcourse.FirstSecurityApp.services.CommentService;
import ru.alishev.springcourse.FirstSecurityApp.services.MessageService;
import ru.alishev.springcourse.FirstSecurityApp.services.TopicService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {
    private final MessageService messageService;

    private final CommentService commentService;
    private final TopicService topicService;

    private int id;
    private int messageId;
    private Date date;

    @Autowired
    public CommentController(MessageService messageService, CommentService commentService, TopicService topicService) {
        this.messageService = messageService;
        this.commentService = commentService;
        this.topicService = topicService;
    }

   // @GetMapping("/message/{messageId}/comments")
    @GetMapping("forum/theme/topic/{id}/message/{messageId}")
    public String showComments(@PathVariable int id,@PathVariable int messageId, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        List<Comment> allInstanceComments = commentService.getAllComments();
        Collections.reverse(allInstanceComments);//что-бы новые сообщения были вверху страницы

        //Comment comments = commentService.getAllCommentsForMessage(messageId);

        model.addAttribute("userRole", userRole);
        model.addAttribute("username", username);
        model.addAttribute("allInstanceComments", allInstanceComments);
        model.addAttribute("topicForm", topicService.findById(id));
        //model.addAttribute("commentForm", new Comment());//отправляем в конструктор
        this.id = id;
        //model.addAttribute("comments", comments);
        return "message";
    }

    //@PostMapping("/message/{messageId}/comments")
    @PostMapping("/forum/theme/topic/{id}/message")
    public String addComment(@ModelAttribute("commentForm") Comment commentForm
                            // ,BindingResult bindingResult)
    ){
//        if (bindingResult.hasErrors()) {
//            return "message";
//        }

        if (commentForm.getId() == 0) {
            commentForm.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            date = new Date();
            commentForm.setDate(date);
            //updateDataPost();
            //commentForm.setTopicId(id);
            commentForm.setMessageId(messageId);
            commentService.addCommentToMessage(messageId,commentForm);
        }
        //return "redirect:/message/" + messageId;
        return "redirect:/forum/theme/topic/" + id + "/message/" + messageId;

    }
}
