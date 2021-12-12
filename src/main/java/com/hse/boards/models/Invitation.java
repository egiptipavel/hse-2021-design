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
    public Long id;

    @Column(nullable = false)
    public Long inviterId;

    @Column(nullable = false)
    public Long recipientId;

    @Column(nullable = false)
    public Long boardId;

    @Column(nullable = false)
    public StatusOfInvitation status;

    public Invitation() {
    }

    public Invitation(Long id, Long inviterId, Long recipientId, Long boardId, StatusOfInvitation status) {
        this.id = id;
        this.inviterId = inviterId;
        this.recipientId = recipientId;
        this.boardId = boardId;
        this.status = status;
    }
}
