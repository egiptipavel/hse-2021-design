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
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column
    public String description;

    public Board() {
    }

    public Board(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
