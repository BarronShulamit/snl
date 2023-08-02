package com.interview.snl.persistence;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class InMemoryBoardRepository {
    public static final int NUM_TILES = 100;

    private static final int MIN_POSITION = 1;
    private static final int MAX_POSITION = 99;
    private static final int MIN_SNAKES_LEADERS = 10;
    private static final int MAX_SNAKES_LEADERS = 20;

    private final HashMap<Integer, Integer> snakesAndLadders = new HashMap<>();
    private final Random random = new Random();

    private int getRandomPosition(Set<Integer> usedPositions) {
        int position;

        do {
            position = random.nextInt(MAX_POSITION) + MIN_POSITION;
        } while (usedPositions.contains(position));

        usedPositions.add(position);

        return position;
    }

    public int getSnakeAndLeadersDestination(int headPosition) {
        return snakesAndLadders.getOrDefault(headPosition, headPosition);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setBoard() {
        Set<Integer> usedPositions = new HashSet<>();

        int numEntries = random.nextInt(MAX_SNAKES_LEADERS - MIN_SNAKES_LEADERS) + MIN_SNAKES_LEADERS;

        for (int i = 0; i < numEntries; i++) {
            snakesAndLadders.put(getRandomPosition(usedPositions), getRandomPosition(usedPositions));
        }
    }
}
