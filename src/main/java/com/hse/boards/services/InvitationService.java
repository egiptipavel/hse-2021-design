package com.hse.boards.services;

import com.hse.boards.exceptions.ServiceException;
import com.hse.boards.models.*;
import com.hse.boards.repositories.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component("InvitationService")
public class InvitationService {
    private final InvitationRepository invitationRepository;

    private final BoardService boardService;

    private final UserService userService;

    @Autowired
    public InvitationService(InvitationRepository invitationRepository,
                             BoardService boardService,
                             UserService userService) {
        this.invitationRepository = invitationRepository;
        this.boardService = boardService;
        this.userService = userService;
    }

    public Optional<Invitation> getInvitationByRecipientIdAndBoardId(long recipientId, long boardId) {
        return invitationRepository.findInvitationByRecipientIdAndBoardId(recipientId, boardId);
    }

    public List<Invitation> getAllUserInvitations() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return invitationRepository.findInvitationsByRecipientId(user.id);
    }

    public List<Invitation> getAllInvitationsFromUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return invitationRepository.findInvitationsByInviterId(user.id);
    }

    @Transactional
    public void inviteToBoard(long recipientId, long boardId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.id == recipientId) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "User can't invite himself");
        }
        BoardToUser boardToUser = boardService.getBoardToUserByBoardIdAndUserId(boardId, user.id)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND,
                        "Board with such id does not exist or user is not a member of this board."));
        if (!boardToUser.admin) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "User is not an admin of this board.");
        }
        User recipient = userService.getUserById(recipientId);
        if (boardService.getBoardToUserByBoardIdAndUserId(boardId, recipient.id).isPresent()) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "This user is already a member of this board");
        }
        if (getInvitationByRecipientIdAndBoardId(recipientId, boardId).isPresent()) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "This user is already invited to this board");
        }
        invitationRepository
                .save(new Invitation(null, user.id, recipientId, boardId, StatusOfInvitation.NOT_CONSIDERED));
    }


    @Transactional
    public void answerOnInvitation(long invitationId, AnswerOnInvitation answer) {
        if (answer == null) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Answer can't be null");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new ServiceException(HttpStatus.BAD_REQUEST, "Invitation with such id does not exist"));
        if (!user.id.equals(invitation.recipientId)) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "This user can't answer on this invitation");
        }
        if (invitation.status == StatusOfInvitation.ACCEPTED ||
                (invitation.status == StatusOfInvitation.REJECTED && answer == AnswerOnInvitation.REJECT)) {
            return;
        }
        if (answer == AnswerOnInvitation.ACCEPT) {
            invitation.status = StatusOfInvitation.ACCEPTED;
            boardService.addUserToBoard(user.id, invitation.boardId);
        } else {
            invitation.status = StatusOfInvitation.REJECTED;
        }
        invitationRepository.save(invitation);
    }
}
