package com.interview.snl.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND)
public class PlayerNotExistException extends RuntimeException {

    public static final String PLAYER_NOT_FOUND = "Player with id %s not found";

    public PlayerNotExistException(String playerId) {
        super(format(PLAYER_NOT_FOUND, playerId));
    }
}
