package com.interview.snl.service;

import com.interview.snl.bean.Player;
import com.interview.snl.bean.PlayerStatus;
import com.interview.snl.enums.Status;
import com.interview.snl.persistence.InMemoryPlayersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class GetPlayerStatus {

    private final InMemoryPlayersRepository playersRepository;

    private Status getGameStatus(Player player) {
        if (player == null) {
            return Status.UNRECOGNIZED;
        } else if (playersRepository.getIsWin(player.getId())) {
            return Status.FINISHED;
        } else {
            return Status.DURING_GAME;
        }
    }

    public PlayerStatus get(String playerId) {
        Player player = playersRepository.getOptionalPlayer(playerId);
        Status gameStatus = getGameStatus(player);
        boolean isRankedFirst = gameStatus == Status.FINISHED && playerId.equals(playersRepository.getPlayerIdWithMinScore());

        return PlayerStatus.builder()
                .id(playerId)
                .status(gameStatus)
                .isRankedFirst(isRankedFirst)
                .score(ofNullable(player).map(Player::getScore).orElse(0))
                .build();
    }
}
