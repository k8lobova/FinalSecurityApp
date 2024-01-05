package ru.alishev.springcourse.FirstSecurityApp.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.FirstSecurityApp.models.Message;


/**
 * @author Neil Alishev
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
