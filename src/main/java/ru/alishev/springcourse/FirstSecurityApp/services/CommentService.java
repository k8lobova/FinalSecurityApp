package ru.alishev.springcourse.FirstSecurityApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstSecurityApp.models.Comment;
import ru.alishev.springcourse.FirstSecurityApp.models.Message;
import ru.alishev.springcourse.FirstSecurityApp.repositories.CommentRepository;
import ru.alishev.springcourse.FirstSecurityApp.repositories.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

//    @Transactional
//    public void save(Comment comment) {
//        commentRepository.save(comment);
//    }

    @Transactional
    public void delete(int id) {
        commentRepository.deleteById(id);
    }

    public Comment findById(int id) {
        Optional<Comment> foundComment = commentRepository.findById(id);
        return foundComment.orElse(null);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }


    public Comment getAllCommentsForMessage(int messageId) {
        return commentRepository.findByMessageId(messageId);
    }

    @Transactional
    public void addCommentToMessage(int messageId, Comment comment) {
        comment.setMessageId(messageId);
        commentRepository.save(comment);
    }


}
