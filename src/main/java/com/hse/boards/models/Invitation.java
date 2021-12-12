package com.hse.boards.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "invitations")
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false)
    public Integer inviterId;

    @Column(nullable = false)
    public Integer recipientId;

    @Column(nullable = false)
    public Integer boardId;

    @Column(nullable = false)
    public StatusOfInvitation status;

    public Invitation() {
    }

    public Invitation(Integer id, Integer inviterId, Integer recipientId, Integer boardId, StatusOfInvitation status) {
        this.id = id;
        this.inviterId = inviterId;
        this.recipientId = recipientId;
        this.boardId = boardId;
        this.status = status;
    }
}
