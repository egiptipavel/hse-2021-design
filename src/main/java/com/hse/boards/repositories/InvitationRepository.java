package com.hse.boards.repositories;

import com.hse.boards.models.Invitation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends CrudRepository<Invitation, Long> {
    Optional<Invitation> findInvitationByRecipientIdAndBoardId(long recipientId, long boardId);

    Optional<List<Invitation>> findInvitationsByRecipientId(long recipientId);

    Optional<List<Invitation>> findInvitationsByInviterId(long inviterId);
}
