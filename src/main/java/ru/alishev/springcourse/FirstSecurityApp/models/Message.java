package ru.alishev.springcourse.FirstSecurityApp.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;


@Entity
@Table(name = "Message")
public class Message {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Сообщение не должно быть пустым")
    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private Date date;

    @Column(name = "username")
    private String username;

    @Column(name = "topic_id")
    private int topicId;

    public Message() {
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", username='" + username + '\'' +
                ", topicId=" + topicId +
                '}';
    }
}
