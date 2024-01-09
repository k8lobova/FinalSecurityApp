package ru.alishev.springcourse.FirstSecurityApp.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.FirstSecurityApp.models.Message;
import ru.alishev.springcourse.FirstSecurityApp.models.Theme;
import ru.alishev.springcourse.FirstSecurityApp.models.Topic;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    Message findByTopicId(int topicId);


}
