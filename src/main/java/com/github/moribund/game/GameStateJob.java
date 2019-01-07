package com.github.moribund.game;

import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.game.GameStatePacket;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import javafx.util.Pair;
import lombok.val;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * The {@code GameStateJob} is a {@link Job} that is scheduled by the {@link org.quartz.Scheduler} and set to
 * execute every 100 MS. The executor is {@link MoribundServer#scheduler}. The game state is {@link org.quartz.Trigger}ed
 * with the identity {@code gameStateJob}. More information on the game state itself exists in {@link GameStatePacket}.
 */
public final class GameStateJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        MoribundServer.getInstance().getGameContainer().forEachGame((gameId, game) -> {
            val gameStatePacket = createGameStatePacket(game);
            game.sendPacketToEveryoneUsingUDP(gameStatePacket);
        });
    }

    /**
     * Creates a packet using all the info in the {@link Game#players} map.
     * @param game The respective {@link Game} that we are creating the packet for.
     * @return The newly created {@link GameStatePacket} packet.
     */
    private GameStatePacket createGameStatePacket(Game game) {
        final ObjectList<Pair<Integer, Pair<Float, Float>>> playerLocations = new ObjectArrayList<>();
        final ObjectList<Pair<Integer, Float>> playerRotations = new ObjectArrayList<>();

        game.forEachPlayer((playerId, player) -> {
            val playerLocation = new Pair<>(player.getX(), player.getY());
            val playerRotation = player.getRotation();

            playerLocations.add(new Pair<>(playerId, playerLocation));
            playerRotations.add(new Pair<>(playerId, playerRotation));
        });
        return new GameStatePacket(playerLocations, playerRotations);
    }
}
