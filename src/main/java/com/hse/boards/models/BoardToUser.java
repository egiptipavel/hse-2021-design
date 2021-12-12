package com.hse.boards.models;

import javax.persistence.*;

@Entity
@Table(name = "board_to_user")
public class BoardToUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false)
    public Integer boardId;

    @Column(nullable = false)
    public Integer userId;

    @Column(nullable = false)
    public Boolean admin;

    public BoardToUser() {
    }

    public BoardToUser(Integer id, Integer boardId, Integer userId, Boolean admin) {
        this.id = id;
        this.boardId = boardId;
        this.userId = userId;
        this.admin = admin;
    }
}