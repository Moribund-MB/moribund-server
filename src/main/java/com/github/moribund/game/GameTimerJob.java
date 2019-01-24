package com.github.moribund.game;

import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.graphics.TimeLeftRefreshPacket;
import lombok.val;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

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
