package com.hse.boards.controllers;

import com.hse.boards.models.Invitation;
import com.hse.boards.services.AnswerOnInvitation;
import com.hse.boards.services.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {
    private final InvitationService invitationService;

    @Autowired
    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping(value = "/board/{boardId}/user/{userId}")
    public ResponseEntity<Void> inviteToBoard(@PathVariable("boardId") long boardId,
                                              @PathVariable("userId") long userId) {
        invitationService.inviteToBoard(userId, boardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Invitation>> getAllUserInvitations() {
        return new ResponseEntity<>(invitationService.getAllUserInvitations(), HttpStatus.OK);
    }

    @GetMapping(value = "/my")
    public ResponseEntity<List<Invitation>> getAllInvitationsFromUser() {
        return new ResponseEntity<>(invitationService.getAllInvitationsFromUser(), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<Void> answerOnInvitation(@PathVariable("id") long id,
                                                   @RequestParam("answer") AnswerOnInvitation answer) {
        invitationService.answerOnInvitation(id, answer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
