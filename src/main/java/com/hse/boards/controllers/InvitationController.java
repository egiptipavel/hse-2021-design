package com.hse.boards.controllers;

import com.hse.boards.services.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {
    private final InvitationService invitationService;

    @Autowired
    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping(value = "/board/{boardId}/user/{userId}")
    public ResponseEntity<Void> inviteToBoard(@PathVariable("boardId") Integer boardId, @PathVariable("userId") Integer userId) {
        invitationService.inviteToBoard(userId, boardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
