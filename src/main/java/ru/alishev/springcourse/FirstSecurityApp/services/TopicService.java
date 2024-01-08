package ru.alishev.springcourse.FirstSecurityApp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstSecurityApp.models.Theme;
import ru.alishev.springcourse.FirstSecurityApp.models.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.alishev.springcourse.FirstSecurityApp.repositories.MessageRepository;
import ru.alishev.springcourse.FirstSecurityApp.repositories.ThemeRepository;
import ru.alishev.springcourse.FirstSecurityApp.repositories.TopicRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ромчи on 25.07.2017.
 */
@Service
@Transactional(readOnly = true)
public class TopicService {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }


    @Transactional
    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    @Transactional
    public void delete(int id) {
        topicRepository.deleteById(id);
    }

    public Topic findById(int id) {
        Optional<Topic> foundTopic = topicRepository.findById(id);
        return foundTopic.orElse(null);
    }

    public List<Topic> getAllTopic() {
        return topicRepository.findAll();
    }

    public Topic findTopicByThemeId(int themeId) {
        return topicRepository.findByThemeId(themeId);   //в этой строчке ошибка  если несколько топиков тема плохо удаляется
    }
    public Page<Topic> findAllTopicsByThemeId(Pageable pageable, int themeId) {
        return topicRepository.findAllByThemeId(pageable, Math.toIntExact(themeId));
        //return topicRepository.findAll(pageable, Math.toIntExact(themeId));23.06 28.12
    }

    public Page<Topic> searchByTopicName(Pageable pageable,String topicName) {
        return topicRepository.findByTopicNameStartingWith(pageable,topicName);
    }

    public Page<Topic> findByTopicNameContaining(Pageable pageable,String topicName) {
        return topicRepository.findByTopicNameContaining(pageable,topicName);
    }

}
