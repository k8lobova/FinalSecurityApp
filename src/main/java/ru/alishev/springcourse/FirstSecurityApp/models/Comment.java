package ru.alishev.springcourse.FirstSecurityApp.models;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@NotEmpty(comment = "Комментарий не должно быть пустым")
    @Column(name = "comment")
    private String comment;

    @Column(name = "username")
    private String username;

    @Column(name = "date")
    private Date date;
    @Column(name = "message_id")
    private int messageId;

    public Comment() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", username='" + username + '\'' +
                ", date=" + date +
                ", messageId=" + messageId +
                '}';
    }
}