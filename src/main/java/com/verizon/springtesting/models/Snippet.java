package com.verizon.springtesting.models;

import jakarta.persistence.*;

@Entity
public class Snippet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String language;
    public String code;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private DBUser user;

//    public Snippet(int id, String language, String code) {
//        this.id = id;
//        this.language = language;
//        this.code = code;
//    }

    public DBUser getUser() { return user; }
    public void setDBUser(DBUser user) { this.user = user; }

    public Integer getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}
