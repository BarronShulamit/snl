package com.interview.snl.service;

import com.interview.snl.bean.Player;
import com.interview.snl.persistence.InMemoryPlayersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddPlayer {

    private final PlayGame playGame;
    private final InMemoryPlayersRepository playersRepository;

    public Player add() {
        Player player = playersRepository.addPlayer();

        playGame.play(player.getId());

        return player;
    }
}
