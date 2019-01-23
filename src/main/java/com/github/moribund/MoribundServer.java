package com.github.moribund;

import com.github.moribund.game.GameContainer;
import com.github.moribund.game.GameStateJob;
import com.github.moribund.net.NetworkBootstrapper;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.val;
import org.quartz.*;

/**
 * The {@code MoribundSever} class represents the entire game.
 */
public class MoribundServer {
    /**
     * The singleton instance of the sever for all classes to access.
     */
    private static MoribundServer instance;

    @Getter
    private final Scheduler scheduler;
    @Getter
    private final HikariDataSource dataSource;

    /**
     * The network bootstrapper to start networking.
     */
    private final NetworkBootstrapper networkBootstrapper;
    @Getter
    private final GameContainer gameContainer;

    /**
     * Constructor that provides the {@code MoribundServer} its dependencies.
     * @param gameContainer The container that holds all the games and functions for them.
     * @param networkBootstrapper The network bootstrapper to start networking.
     * @param dataSource The Hikari datasource to establish an SQL connection.
     */
    MoribundServer(GameContainer gameContainer, NetworkBootstrapper networkBootstrapper, Scheduler scheduler, HikariDataSource dataSource) {
        this.gameContainer = gameContainer;
        this.networkBootstrapper = networkBootstrapper;
        this.scheduler = scheduler;
        this.dataSource = dataSource;
    }

    /**
     * Starts the server by connecting to the client.
     */
    void start() {
        connectNetworking();
        startScheduler();
        scheduleGameState();
    }

    /**
     * Starts a {@link Scheduler}.
     */
    private void startScheduler() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Schedules the {@link GameStateJob}. For more information of the game state, refer to both {@link GameStateJob}
     * and {@link com.github.moribund.net.packets.game.GameStatePacket}.
     */
    private void scheduleGameState() {
        try {
            val timePerTick = 100;
            val scheduledTime = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(timePerTick).repeatForever();
            val gameStateJobDetail = JobBuilder.newJob(GameStateJob.class).withIdentity("gameStateJob").build();
            val trigger = TriggerBuilder.newTrigger().withIdentity("gameState").withSchedule(scheduledTime).build();

            scheduler.scheduleJob(gameStateJobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connects the server to the {@link com.esotericsoftware.kryonet.Client}.
     */
    private void connectNetworking() {
        networkBootstrapper.connect();
    }

    /**
     * Gets the singleton instance of the server.
     * @return The singleton instance.
     */
    public static MoribundServer getInstance() {
        if (instance == null) {
            val serverFactory = new MoribundServerFactory();
            instance = serverFactory.createServer();
        }
        return instance;
    }
}