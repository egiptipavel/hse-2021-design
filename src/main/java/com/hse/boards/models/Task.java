package com.hse.boards.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

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
    public Integer creatorId;

    @Column
    public Integer ownerId;

    @Column(nullable = false)
    public String name;

    public String description;

    @Column
    public Timestamp deadline;

    @Column
    public StatusOfTask status;

    public Task() {
    }

    public Task(Integer id, Integer boardId, Integer creatorId, Integer ownerId, String name, String description,
                Timestamp deadline, StatusOfTask status) {
        this.id = id;
        this.boardId = boardId;
        this.creatorId = creatorId;
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
    }
}
