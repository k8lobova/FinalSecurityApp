package ru.alishev.springcourse.FirstSecurityApp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstSecurityApp.models.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.alishev.springcourse.FirstSecurityApp.repositories.TopicRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TopicService {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public boolean isTopicNameUnique(String topicName) {
        // Проверяем, существует ли тема с указанным именем
        return topicRepository.findByTopicName(topicName) == null;
    }

    @Transactional
    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    @Transactional
    public void delete(int id) {
        topicRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll(List<Topic> topics) {
        topicRepository.deleteAll(topics);
    }

    public Topic findById(int id) {
        Optional<Topic> foundTopic = topicRepository.findById(id);
        return foundTopic.orElse(null);
    }

    public List<Topic> getAllTopic() {
        return topicRepository.findAll();
    }

    public List<Topic> findTopicsByThemeId(int themeId) {
        return topicRepository.findByThemeId(themeId);   //в этой строчке была ошибка если несколько топиков тема плохо удаляется
    }

    public Page<Topic> findAllTopicsByThemeId(Pageable pageable, int themeId) {
        return topicRepository.findAllByThemeId(pageable, Math.toIntExact(themeId));
        //return topicRepository.findAll(pageable, Math.toIntExact(themeId));23.06 28.12
    }

    public List<Topic> searchByTopicName(String topicName) {
        return topicRepository.findByTopicNameStartingWith(topicName);
    }

    public Page<Topic> searchByTopicNameAndThemeId(Pageable pageable, String topicName, int themeId) {
        List<Topic> byName = searchByTopicName(topicName);
        List<Topic> topicList = findTopicsByThemeId(themeId);
        topicList.retainAll(byName);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), topicList.size());
        return new PageImpl<>(topicList.subList(start, end), pageable, topicList.size());
    }

    public Page<Topic> findByTopicNameContaining(Pageable pageable, String topicName) {
        return topicRepository.findByTopicNameContaining(pageable, topicName);
    }

}
