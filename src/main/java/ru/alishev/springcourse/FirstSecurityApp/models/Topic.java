package ru.alishev.springcourse.FirstSecurityApp.models;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by Ромчи on 25.07.2017.
 */
@Entity
@Table(name = "topic")
public class Topic implements Comparable<Topic> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")//знаем создателя топика
    private String username;

    @Column(name = "topicName")//знаем ися топика
    private String topicName;

    @Column(name = "description")//описание
    private String description;

    @Column(name = "lastPostDate")//знаем время послед сообщения
    private Date lastPostDate;

    @Column(name = "themeId")//знаем к какой теме привязан топик
    private int themeId;

    public Topic() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastPostDate() {
        return lastPostDate;
    }

    public void setLastPostDate(Date lastPostDate) {
        this.lastPostDate = lastPostDate;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    @Override
    public int compareTo(Topic that) {
        return that.lastPostDate.compareTo(this.getLastPostDate());
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", topicName='" + topicName + '\'' +
                ", description='" + description + '\'' +
                ", lastPostDate=" + lastPostDate +
                ", themeId=" + themeId +
                '}';
    }
}
