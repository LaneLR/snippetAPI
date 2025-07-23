package com.verizon.springtesting.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "db_user")
public class DBUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String email;
    public String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Snippet> snippets;
}
