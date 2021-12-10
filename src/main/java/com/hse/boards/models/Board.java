package com.hse.boards.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false)
    public Integer userId;

    @Column(nullable = false)
    public Boolean admin;

    public Board() {
    }

    public Board(Integer id, Integer userId, Boolean admin) {
        this.id = id;
        this.userId = userId;
        this.admin = admin;
    }
}
