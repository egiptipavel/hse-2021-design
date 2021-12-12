package com.hse.boards.repositories;

import com.hse.boards.models.Invitation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InvitationRepository extends CrudRepository<Invitation, Integer> {
    Optional<Invitation> findInvitationByRecipientIdAndBoardId(Integer recipientId, Integer boardId);
}
