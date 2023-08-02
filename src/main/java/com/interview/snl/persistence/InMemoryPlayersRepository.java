package com.interview.snl.persistence;

import com.interview.snl.bean.Player;
import com.interview.snl.exception.PlayerNotExistException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.interview.snl.persistence.InMemoryBoardRepository.NUM_TILES;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;

@Service
public class InMemoryPlayersRepository {
    private final HashMap<String, Player> players = new HashMap<>();

    public Player addPlayer() {
        String id = UUID.randomUUID().toString();

        // Game starts from position 0.
        Player player = Player.builder()
                .id(id)
                .build();

        players.put(id, player);

        return player;
    }

    public Player getOptionalPlayer(String playerId) {
        return players.get(playerId);
    }

    public Player getPlayer(String playerId) {
        return Optional.of(players.get(playerId))
                .orElseThrow(() -> new PlayerNotExistException(playerId));
    }

    public void updatePlayer(String playerId, Player updated) {
        ofNullable(players.replace(playerId, updated))
                .orElseThrow(() -> new PlayerNotExistException(playerId));
    }

    public boolean getIsWin(String playerId) {
        return getPlayer(playerId).getPosition() == NUM_TILES;
    }

    public String getPlayerIdWithMinScore() {
        return players.entrySet().stream()
                .min(comparing(entry -> entry.getValue().getScore()))
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
