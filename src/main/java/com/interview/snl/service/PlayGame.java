package com.interview.snl.service;

import com.interview.snl.bean.Player;
import com.interview.snl.persistence.InMemoryBoardRepository;
import com.interview.snl.persistence.InMemoryPlayersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.interview.snl.persistence.InMemoryBoardRepository.NUM_TILES;

@Service
@RequiredArgsConstructor
public class PlayGame {
    private static final int MIN_DICE_VALUE = 1;
    private static final int MAX_DICE_VALUE = 6;

    private final InMemoryBoardRepository boardRepository;
    private final InMemoryPlayersRepository playersRepository;
    private final Random random = new Random();

    private int roll() {
        return random.nextInt(MAX_DICE_VALUE) + MIN_DICE_VALUE;
    }

    private int calculatePlayerNewPosition(int currentPosition, int diceRoll) {
        int newPosition = currentPosition + diceRoll;

        // Arrive exactly at the last square.
        // If the cube shows extra points, the player must move to their destination and continue to move in the opposite direction of the remaining points.
        if (newPosition > NUM_TILES) {
            newPosition = NUM_TILES - (newPosition - NUM_TILES);
        }

        return boardRepository.getSnakeAndLeadersDestination(newPosition);
    }

    @Async
    public void play(String playerId) {
        while (!playersRepository.getIsWin(playerId)) {
            Player player = playersRepository.getPlayer(playerId);

            int diceRoll = roll();
            int newPosition = calculatePlayerNewPosition(player.getPosition(), diceRoll);
            int newScore = player.getScore() + 1;

            Player updatedPlayer = player.toBuilder()
                    .position(newPosition)
                    .score(newScore)
                    .build();

            playersRepository.updatePlayer(playerId, updatedPlayer);
        }
    }
}
