package com.hse.boards.services;

import com.hse.boards.exceptions.ServiceException;
import com.hse.boards.models.BoardToUser;
import com.hse.boards.models.Invitation;
import com.hse.boards.models.StatusOfInvitation;
import com.hse.boards.models.User;
import com.hse.boards.repositories.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component("InvitationService")
public class InvitationService {
    private final InvitationRepository invitationRepository;

    private final BoardService boardService;

    private final UserService userService;

    @Autowired
    public InvitationService(InvitationRepository invitationRepository, BoardService boardService, UserService userService) {
        this.invitationRepository = invitationRepository;
        this.boardService = boardService;
        this.userService = userService;
    }

    public Optional<Invitation> getInvitationByRecipientIdAndBoardId(int recipientId, int boardId) {
        return invitationRepository.findInvitationByRecipientIdAndBoardId(recipientId, boardId);
    }

    @Transactional
    public void inviteToBoard(int recipientId, int boardId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
        invitationRepository.save(new Invitation(null, user.id, recipientId, boardId, StatusOfInvitation.NOT_CONSIDERED));
    }
}
