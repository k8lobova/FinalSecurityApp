package ru.alishev.springcourse.FirstSecurityApp.models;


import javax.persistence.*;
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
    private Long id;

    @Column(name = "themeName")
    private String themeName;

    @Column(name = "description")//описание
    private String description;

    @Column(name = "lastPostDate")
    private Date lastPostDate;

    public Theme() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
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
