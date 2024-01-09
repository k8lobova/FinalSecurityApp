package ru.alishev.springcourse.FirstSecurityApp.models;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Ромчи on 25.07.2017.
 */
@Entity
@Table(name = "Theme")
public class Theme implements Comparable<Theme> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotEmpty(message = "Имя темы не должно быть пустым")
    @Size(max = 100, message = "Имя темы должно до 100 символов длиной")
    @Column(name = "theme_name")
    private String themeName;

    @Column(name = "description")//описание
    private String description;

    @Column(name = "last_post_date")
    private Date lastPostDate;

    public Theme() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
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

    @Override
    public int compareTo(Theme that) {
        return that.lastPostDate.compareTo(this.getLastPostDate());
    }

    @Override
    public String toString() {
        return "Theme{" +
                "id=" + id +
                ", themeName='" + themeName + '\'' +
                ", description='" + description + '\'' +
                ", lastPostDate=" + lastPostDate +
                '}';
    }
}
