package com.github.moribund.game;

import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.graphics.TimeLeftRefreshPacket;
import com.github.moribund.objects.playable.players.PlayableCharacter;
import lombok.val;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * A {@code GameTimerJob} is a {@link Job} that decrements the {@link PlayableCharacter#getTimeLeft()} for
 * each {@link PlayableCharacter} in the game.
 */
public class GameTimerJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        val gameId = context.getJobDetail().getJobDataMap().getInt("gameId");
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);

        game.forEachPlayer(player -> {
            player.getTimeLeft().decrementTime(1);
            val timeLeftRefreshPacket = new TimeLeftRefreshPacket(player.getTimeLeft().toString());
            player.getConnection().sendUDP(timeLeftRefreshPacket);
        });
    }
}
