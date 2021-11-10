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
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column(nullable = false)
    public Integer ownerId;

    public Board() {
    }

    public Board(Integer id, Integer ownerId) {
        this.id = id;
        this.ownerId = ownerId;
    }
}
