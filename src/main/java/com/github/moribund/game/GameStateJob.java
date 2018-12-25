package com.github.moribund.game;

import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.game.GameStatePacket;
import javafx.util.Pair;
import lombok.val;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.ArrayList;
import java.util.List;

public class GameStateJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        val gameStatePacket = createGameStatePacket();
        MoribundServer.getInstance().sendPacketToEveryone(gameStatePacket);
    }

    private GameStatePacket createGameStatePacket() {
        val players = MoribundServer.getInstance().getPlayers();
        final List<Pair<Integer, Pair<Float, Float>>> playerLocations = new ArrayList<>();
        final List<Pair<Integer, Float>> playerRotations = new ArrayList<>();

        players.forEach((playerId, player) -> {
            val playerLocation = new Pair<>(player.getX(), player.getY());
            playerLocations.add(new Pair<>(playerId, playerLocation));
        });
        players.forEach((playerId, player) -> {
            val playerRotation = player.getRotation();
            playerRotations.add(new Pair<>(playerId, playerRotation));
        });

        return new GameStatePacket(playerLocations, playerRotations);
    }
}
