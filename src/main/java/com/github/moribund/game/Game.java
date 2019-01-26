package com.github.moribund.game;

import com.github.moribund.GraphicalConstants;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.OutgoingPacket;
import com.github.moribund.objects.nonplayable.GroundItem;
import com.github.moribund.objects.nonplayable.ItemType;
import com.github.moribund.objects.playable.PlayableCharacter;
import com.github.moribund.objects.playable.Player;
import com.github.moribund.utils.ArtificialTime;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lombok.*;
import org.quartz.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * A single game that is going on. Multiple games go in parallel. The method that judges the creation of
 * a new game is {@link GameContainer#getAvailableGame()}.
 */
public class Game {

    /**
     * The ID of the game running.
     */
    @Getter
    private final int gameId;

    /**
     * The map of players in the game, with the player IDs mapping to the player.
     */
    private final Int2ObjectMap<PlayableCharacter> players;

    /**
     * The queue of packets to send on the next {@link GameStateJob}.
     */
    @Getter(value = AccessLevel.PACKAGE)
    private final Queue<OutgoingPacket> outgoingPacketsQueue;

    /**
     * The list of ground items in the game.
     */
    @Getter
    private final ObjectSet<GroundItem> groundItems;

    /**
     * Has the game started?
     */
    @Getter @Setter(AccessLevel.PACKAGE)
    private boolean started;

    /**
     * Has the game finished?
     */
    @Getter
    private boolean finished;

    Game(int gameId) {
        this.gameId = gameId;
        players = new Int2ObjectOpenHashMap<>();
        groundItems = new ObjectArraySet<>();
        outgoingPacketsQueue = new LinkedList<>();
        started = false;
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

    /**
     * Empties the queue of packets.
     */
    void emptyQueue() {
        outgoingPacketsQueue.clear();
    }

    /**
     * Adds a player to a game.
     * @param playerId The player ID of the player.
     * @param player The player to add.
     */
    public void addPlayer(int playerId, Player player) {
        int countBefore = players.size();
        players.putIfAbsent(playerId, player);
        if (!started && countBefore == GameContainer.MINIMUM_PLAYERS - 1) {
            startCountdownForGame();
        }
    }

    /**
     * Starts the countdown for the lobby timer of the game.
     */
    private void startCountdownForGame() {
        try {
            val repetitionTime = 1;
            val scheduledTime = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(repetitionTime)
                    .withRepeatCount(GameContainer.COUNTDOWN_TIME);
            val counterDataMap = new JobDataMap();
            counterDataMap.put("counter", new ArtificialTime(GameContainer.COUNTDOWN_TIME));
            val gameTimerJobDetail = JobBuilder.newJob(GameStartJob.class)
                    .withIdentity("gameStartJob" + gameId)
                    .usingJobData("gameId", gameId)
                    .usingJobData(counterDataMap)
                    .build();
            var trigger = TriggerBuilder.newTrigger().withIdentity("gameStart" + gameId).withSchedule(scheduledTime).build();

            MoribundServer.getInstance().getScheduler().start();
            MoribundServer.getInstance().getScheduler().scheduleJob(gameTimerJobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes the player from the game.
     * @param playerId The player ID of the player to remove.
     */
    public void removePlayer(int playerId) {
        players.remove(playerId);
        if (players.size() == 1 && started) {
            sendVictoryRoyale();
            endGame();
        } else if (players.size() == 0 && started) {
            MoribundServer.getInstance().getGameContainer().removeGame(gameId);
        }
    }

    /**
     * Sends a victory royale to the last player left.
     */
    private void sendVictoryRoyale() {
        
    }

    /**
     * Does an action for each player.
     * @param playerConsumer the action to do for each player.
     */
    public void forEachPlayer(Consumer<PlayableCharacter> playerConsumer) {
        players.values().forEach(playerConsumer);
    }

    /**
     * Does an action for each player, providing their player ID too.
     * @param playerConsumer The action to do for each player.
     */
    public void forEachPlayer(BiConsumer<Integer, PlayableCharacter> playerConsumer) {
        players.forEach(playerConsumer);
    }

    /**
     * Gets a player by their ID.
     * @param playerId The player ID of the player to get.
     * @return The player.
     */
    public PlayableCharacter getPlayableCharacter(int playerId) {
        return players.get(playerId);
    }

    /**
     * Gets how many players are left in the game.
     * @return The amount of players left in the game.
     */
    int getPlayerAmount() {
        return players.size();
    }

    /**
     * Checks to see if a player is in the game.
     * @param playerId The ID of the player.
     * @return If the player is in the game.
     */
    boolean containsPlayer(int playerId) {
        return players.containsKey(playerId);
    }

    /**
     * Sets up the game by spawning the inital items on the ground.
     */
    void setup() {
        val itemsOnGround = ThreadLocalRandom.current().nextInt(30, 40);
        for (int i = 0; i < itemsOnGround; i++) {
            val itemType = ItemType.random();
            val x = (float) ThreadLocalRandom.current().nextDouble(GraphicalConstants.MINIMUM_X, GraphicalConstants.MAXIMUM_X);
            val y = (float) ThreadLocalRandom.current().nextDouble(GraphicalConstants.MINIMUM_Y, GraphicalConstants.MAXIMUM_Y);
            val groundItem = new GroundItem(itemType, x, y);
            groundItems.add(groundItem);
        }
    }

    public void addGroundItem(GroundItem groundItem) {
        groundItems.add(groundItem);
    }

    /**
     * Gets a ground item at a given id, x-coordinate, and y-coordinate.
     * @return The ground item.
     */
    public GroundItem getGroundItem(int id, float x, float y) {
        for (GroundItem groundItem : groundItems) {
            if (groundItem.matches(id, x, y)) {
                return groundItem;
            }
        }
        return null;
    }

    /**
     * Removes a ground item from the game.
     * @param groundItem The ground item to remove.
     */
    public void removeGroundItem(GroundItem groundItem) {
        groundItems.remove(groundItem);
    }

    /**
     * Ends a game by stopping its game timer, marking {@link Game#started} as false, and marking
     * {@link Game#finished} as true.
     */
    void endGame() {
        try {
            MoribundServer.getInstance().getScheduler().deleteJob(new JobKey("gameTimer" + gameId));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        started = false;
        finished = true;
    }
}
