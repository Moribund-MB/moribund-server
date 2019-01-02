package com.github.moribund.game;

import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.game.GameStatePacket;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import javafx.util.Pair;
import lombok.val;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class GameStateJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        val gameStatePacket = createGameStatePacket();
        MoribundServer.getInstance().sendPacketToEveryone(gameStatePacket);
    }

    private GameStatePacket createGameStatePacket() {
        val players = MoribundServer.getInstance().getPlayers();
        final ObjectList<Pair<Integer, Pair<Float, Float>>> playerLocations = new ObjectArrayList<>();
        final ObjectList<Pair<Integer, Float>> playerRotations = new ObjectArrayList<>();

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
