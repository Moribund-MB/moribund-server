package com.github.moribund;

import com.github.moribund.entity.PlayableCharacter;
import com.github.moribund.game.GameStateJob;
import com.github.moribund.net.NetworkBootstrapper;
import com.github.moribund.net.packets.OutgoingPacket;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
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

    /**
     * All the {@link PlayableCharacter}s in the game.
     */
    @Getter
    private final Int2ObjectMap<PlayableCharacter> players;

    private final Scheduler scheduler;

    /**
     * The network bootstrapper to start networking.
     */
    private final NetworkBootstrapper networkBootstrapper;

    /**
     * Constructor that provides the {@code MoribundServer} its dependencies.
     * @param players The list of players in the entire game.
     * @param networkBootstrapper The network bootstrapper to start networking.
     */
    MoribundServer(Int2ObjectMap<PlayableCharacter> players, NetworkBootstrapper networkBootstrapper, Scheduler scheduler) {
        this.players = players;
        this.networkBootstrapper = networkBootstrapper;
        this.scheduler = scheduler;
    }

    /**
     * Starts the server by connecting to the client.
     */
    void start() {
        connectNetworking();
        startScheduler();
        scheduleGameState();
    }

    private void startScheduler() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

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
     * Sends an object, or a packet, to all the {@link MoribundServer#players} through TCP.
     * @param outgoingPacket The outgoing packet to send everyone.
     */
    public void sendPacketToEveryone(OutgoingPacket outgoingPacket) {
        players.forEach((playerId, player) -> player.getConnection().sendUDP(outgoingPacket));
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