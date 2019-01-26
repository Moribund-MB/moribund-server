package com.github.moribund.game;

import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.graphics.LobbyTimeLeftRefreshPacket;
import com.github.moribund.utils.ArtificialTime;
import lombok.val;
import lombok.var;
import org.quartz.*;

/**
 * A {@code GameStartJob} is a {@link Job} that counts down the time for a lobby. When the time is zero,
 * it sets the game as started and schedules the {@link GameTimerJob}.
 */
public class GameStartJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        val gameId = context.getJobDetail().getJobDataMap().getInt("gameId");
        val counter = (ArtificialTime) context.getJobDetail().getJobDataMap().get("counter");
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);

        if (counter.getTime() == 0) {
            game.setStarted(true);
            scheduleGameTimer(gameId);
        }

        counter.decrementTime(1);
        game.queuePacket(new LobbyTimeLeftRefreshPacket(counter.toString()));
    }

    /**
     * Schedules a {@link GameTimerJob}.
     * @param gameId The game ID to start the job for.
     */
    private void scheduleGameTimer(int gameId) {
        try {
            val timePerTick = 1;
            val scheduledTime = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(timePerTick).repeatForever();
            val gameTimerJobDetail = JobBuilder.newJob(GameTimerJob.class)
                    .withIdentity("gameTimerJob" + gameId)
                    .usingJobData("gameId", gameId)
                    .build();
            var trigger = TriggerBuilder.newTrigger().withIdentity("gameTimer" + gameId).withSchedule(scheduledTime).build();

            MoribundServer.getInstance().getScheduler().start();
            MoribundServer.getInstance().getScheduler().scheduleJob(gameTimerJobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
