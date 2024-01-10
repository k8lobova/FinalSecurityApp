package ru.alishev.springcourse.FirstSecurityApp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.FirstSecurityApp.models.Topic;

import java.util.List;


@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

    List<Topic> findByThemeId(int themeId);

    Page<Topic> findAllByThemeId(Pageable pageable, int themeId);

    List<Topic> findByTopicNameStartingWith(String topicName);

    Page<Topic> findByTopicNameContaining(Pageable pageable, String topicName);

    Topic findByTopicName(String topicName);


}
