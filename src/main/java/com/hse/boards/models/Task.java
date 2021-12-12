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
    public Long id;

    @Column(nullable = false)
    public Long boardId;

    @Column(nullable = false)
    public Long creatorId;

    @Column
    public Long ownerId;

    @Column(nullable = false)
    public String name;

    public String description;

    @Column
    public Timestamp deadline;

    @Column
    public StatusOfTask status;

    public Task() {
    }

    public Task(Long id, Long boardId, Long creatorId, Long ownerId, String name, String description,
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
