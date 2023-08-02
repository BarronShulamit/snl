package com.interview.snl.controller;

import com.interview.snl.bean.Player;
import com.interview.snl.bean.PlayerStatus;
import com.interview.snl.service.AddPlayer;
import com.interview.snl.service.GetPlayerStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("snl/players")
@Tag(name = "Snakes and ladders controller")
public class SnlController {

    private final AddPlayer addPlayer;
    private final GetPlayerStatus getPlayerStatus;

    @PostMapping
    @Operation(summary = "Add a new player")
    public Player addPlayer() {
        return addPlayer.add();
    }

    @GetMapping("{playerId}/status")
    @Operation(summary = "Get player status")
    public PlayerStatus getStatus(@PathVariable String playerId) {
        return getPlayerStatus.get(playerId);
    }
}
