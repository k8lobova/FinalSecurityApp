package ru.alishev.springcourse.FirstSecurityApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.FirstSecurityApp.models.Message;
import ru.alishev.springcourse.FirstSecurityApp.models.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByMessageId(int messageId);

    //List<Comment> getAllCommentsForMessage(Long messageId);
    //addCommentToMessage(Long messageId, Comment comment);


}
