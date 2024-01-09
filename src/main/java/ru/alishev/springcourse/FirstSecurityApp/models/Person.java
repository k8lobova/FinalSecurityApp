package ru.alishev.springcourse.FirstSecurityApp.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Neil Alishev
 */
@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Логин не должнен быть пустым")
    @Size(min = 2, max = 100, message = "Логин должен быть от 2 до 100 символов длиной")
    @Column(name = "username")
    private String username;

    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    @Max(value = 2023, message = "Год рождения должен быть меньше, чем 2023")
    @Column(name = "yearofbirth")
    private int yearofbirth;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    @Column(name = "firstname")
    private String firstname;

    @NotEmpty(message = "Фамилия не должна быть пустой")
    @Size(min = 2, max = 100, message = "Фамилия должна быть от 2 до 100 символов длиной")
    @Column(name = "lastname")
    private String lastname;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;


    // Конструктор по умолчанию нужен для Spring
    public Person() {
    }

    public Person(String username, int yearOfBirth) {
        this.username = username;
        this.yearofbirth = yearofbirth;
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

    public int getYearofbirth() {
        return yearofbirth;
    }

    public void setYearofbirth(int yearofbirth) {
        this.yearofbirth = yearofbirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", yearOfBirth=" + yearofbirth +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}