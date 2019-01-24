package com.github.moribund.game;

import com.github.moribund.MoribundServer;
import com.github.moribund.utils.ArtificialTime;
import lombok.val;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class GameStartJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        val gameId = context.getJobDetail().getJobDataMap().getInt("gameId");
        val counter = (ArtificialTime) context.getJobDetail().getJobDataMap().get("counter");
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);

        if (counter.getTime() == 0) {
            game.setStarted(true);
        }

        // queue time packet
    }
}
