package ru.neustupov.model;

import javax.persistence.*;
import java.util.Date;

@Entity                                                //класс сущности
public class User {

    @Id                                                //primary key в базе
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)    //auto-increment
    private int id;

    @Column
    private String name;

    @Column
    private int age;

    @Column(columnDefinition = "BOOLEAN")             //тип поля в базе
    private boolean isAdmin;

    @Column(columnDefinition = "TIMESTAMP")
    private Date date;

    public User() {
    }

    public User(String name, int age, boolean isAdmin, Date date) {
        super();
        this.name = name;
        this.age = age;
        this.isAdmin = isAdmin;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
