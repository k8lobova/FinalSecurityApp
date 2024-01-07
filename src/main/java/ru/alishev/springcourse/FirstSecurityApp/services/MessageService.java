package ru.alishev.springcourse.FirstSecurityApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstSecurityApp.models.Message;
import ru.alishev.springcourse.FirstSecurityApp.models.Topic;
import ru.alishev.springcourse.FirstSecurityApp.repositories.MessageRepository;
import ru.alishev.springcourse.FirstSecurityApp.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Transactional
    public void delete(int id) {
        messageRepository.deleteById(id);
    }

    public Message findById(int id) {
        Optional<Message> foundMessage = messageRepository.findById(id);
        return foundMessage.orElse(null);
    }
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }


    public Message findMessageByTopicId(int topicId) {
        return messageRepository.findByTopicId(topicId);
    }



}
