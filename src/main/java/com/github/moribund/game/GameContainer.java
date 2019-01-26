package com.github.moribund.game;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import lombok.val;

import java.util.function.BiConsumer;

/**
 * The container that contains all the currently running games.
 */
public class GameContainer {

    /**
     * The maximum player capacity for a game.
     */
    private static final int MAXIMUM_PLAYERS = 4;

    /**
     * The minimum player amount for a game.
     */
    static final int MINIMUM_PLAYERS = 2;

    /**
     * The lobby timer for the game.
     */
    static final int COUNTDOWN_TIME = 2;

    /**
     * The map of games running associated with their game IDs.
     */
    private final Int2ObjectMap<Game> games;

    /**
     * The next game ID to use when generating a new game.
     */
    private int nextGameId;

    public GameContainer(Int2ObjectMap<Game> games) {
        this.games = games;
    }

    /**
     * Gets a game by its game ID.
     * @param gameId The game ID of the game.
     * @return The {@link Game}.
     */
    public Game getGame(int gameId) {
        return games.get(gameId);
    }

    /**
     * Gets an unknown game for a given player ID.
     * @param playerId The player ID to search for.
     * @return The game found.
     */
    public Game getGameForPlayerId(int playerId) {
        for (Game game : games.values()) {
            if (game.containsPlayer(playerId)) {
                return game;
            }
        }
        return null;
    }

    /**
     * Creates a game and {@link Game#setup()}s it.
     * @return The newly made game.
     */
    private Game createGame() {
        val game = new Game(nextGameId);
        games.put(nextGameId, game);
        game.setup();
        nextGameId++;
        return game;
    }

    /**
     * Gets an available, currently not started game. If not found, it creates a new game and returned that.
     * This method cannot return null.
     * @return The next game available.
     */
    public Game getAvailableGame() {
        for (Game game : games.values()) {
            if (game.getPlayerAmount() < MAXIMUM_PLAYERS && !game.isStarted() && !game.isFinished()) {
                return game;
            }
        }
        return createGame();
    }

    /**
     * Removes idle games in which there are no players in them.
     */
    public void removeIdleGames() {
        ObjectList<Integer> idleGameIds = new ObjectArrayList<>();
        games.forEach((gameId, game) -> {
            if (game.getPlayerAmount() == 0) {
                game.endGame();
                idleGameIds.add(gameId);
            }
        });
        for (int idleGameId : idleGameIds) {
            games.remove(idleGameId);
        }
    }

    /**
     * Does an action for each game, giving the ID of the game too.
     * @param gameBiConsumer The action to do for each game.
     */
    void forEachGame(BiConsumer<Integer, Game> gameBiConsumer) {
        games.forEach(gameBiConsumer);
    }

    /**
     * Removes a game using a game ID.
     * @param gameId The game ID of the game to remove.
     */
    void removeGame(int gameId) {
        games.remove(gameId);
    }
}
