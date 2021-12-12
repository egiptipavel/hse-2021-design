package com.hse.boards.models;

import javax.persistence.*;

@Entity
@Table(name = "board_to_user")
public class BoardToUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public Long boardId;

    @Column(nullable = false)
    public Long userId;

    @Column(nullable = false)
    public Boolean admin;

    public BoardToUser() {
    }

    public BoardToUser(Long id, Long boardId, Long userId, Boolean admin) {
        this.id = id;
        this.boardId = boardId;
        this.userId = userId;
        this.admin = admin;
    }
}