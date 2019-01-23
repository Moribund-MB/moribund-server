package com.github.moribund.game;

import com.github.moribund.GraphicalConstants;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.OutgoingPacket;
import com.github.moribund.objects.nonplayable.GroundItem;
import com.github.moribund.objects.nonplayable.ItemType;
import com.github.moribund.objects.playable.PlayableCharacter;
import com.github.moribund.objects.playable.Player;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.val;
import org.quartz.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Game {
    @Getter
    private final int gameId;
    private final Int2ObjectMap<PlayableCharacter> players;
    @Getter(value = AccessLevel.PACKAGE)
    private final Queue<OutgoingPacket> outgoingPacketsQueue;
    @Getter
    private final ObjectSet<GroundItem> groundItems;

    Game(int gameId) {
        this.gameId = gameId;
        players = new Int2ObjectOpenHashMap<>();
        groundItems = new ObjectArraySet<>();
        outgoingPacketsQueue = new LinkedList<>();
        scheduleGameTimer();
    }

    private void scheduleGameTimer() {
        try {
            val timePerTick = 1;
            val scheduledTime = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(timePerTick).repeatForever();
            val gameTimerJobDetail = JobBuilder.newJob(GameTimerJob.class).withIdentity("gameTimerJob" + gameId).build();
            val trigger = TriggerBuilder.newTrigger().withIdentity("gameTimer" + gameId).withSchedule(scheduledTime).build();
            // todo make better trigger to follow quartz practices

            gameTimerJobDetail.getJobDataMap().put("gameId", gameId);
            MoribundServer.getInstance().getScheduler().start();

            // TODO NEED TO FIND A BETTER WAY THAN THIS MESS
            MoribundServer.getInstance().getScheduler().scheduleJob(gameTimerJobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends an object, or a packet, to all the {@link Game#players}.
     * @param outgoingPacket The outgoing packet to send everyone.
     */
    public void sendPacketToEveryoneUsingUDP(OutgoingPacket outgoingPacket) {
        players.forEach((playerId, player) -> player.getConnection().sendUDP(outgoingPacket));
    }

    public void sendPacketToEveryoneUsingTCP(OutgoingPacket outgoingPacket) {
        players.forEach((playerId, player) -> player.getConnection().sendTCP(outgoingPacket));
    }

    public void queuePacket(OutgoingPacket outgoingPacket) {
        outgoingPacketsQueue.add(outgoingPacket);
    }

    public void emptyQueue() {
        outgoingPacketsQueue.clear();
    }

    public void addPlayer(int playerId, Player player) {
        players.putIfAbsent(playerId, player);
    }

    public void removePlayer(int playerId) {
        players.remove(playerId);
    }

    public void forEachPlayer(Consumer<PlayableCharacter> playerConsumer) {
        players.values().forEach(playerConsumer);
    }

    public void forEachPlayer(BiConsumer<Integer, PlayableCharacter> playerConsumer) {
        players.forEach(playerConsumer);
    }

    public PlayableCharacter getPlayableCharacter(int playerId) {
        return players.get(playerId);
    }

    public int getPlayerAmount() {
        return players.size();
    }

    public boolean containsPlayer(int playerId) {
        return players.containsKey(playerId);
    }

    void setup() {
        val itemsOnGround = ThreadLocalRandom.current().nextInt(10, 15);
        for (int i = 0; i < itemsOnGround; i++) {
            val itemType = ItemType.random();
            val x = (float) ThreadLocalRandom.current().nextDouble(GraphicalConstants.MINIMUM_X, GraphicalConstants.MAXIMUM_X);
            val y = (float) ThreadLocalRandom.current().nextDouble(GraphicalConstants.MINIMUM_Y, GraphicalConstants.MAXIMUM_Y);
            val groundItem = new GroundItem(itemType, x, y);
            groundItems.add(groundItem);
        }
    }

    public GroundItem getGroundItem(int id, float x, float y) {
        for (GroundItem groundItem : groundItems) {
            if (groundItem.matches(id, x, y)) {
                return groundItem;
            }
        }
        return null;
    }

    public void removeGroundItem(GroundItem groundItem) {
        groundItems.remove(groundItem);
    }
}
