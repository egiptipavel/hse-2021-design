package com.hse.boards.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

enum Status {
    OPEN,
    READY,
    FOR_CHECKING,
    CLOSED
}

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false)
    public Integer boardId;

    @Column(nullable = false)
    public String name;

    public String description;

    @Column
    public Timestamp deadline;

    @Column
    public Status status;

    public Task() {
    }

    public Task(Integer id, Integer boardId, String name, String description, Timestamp deadline, Status status) {
        this.id = id;
        this.boardId = boardId;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
    }
}
