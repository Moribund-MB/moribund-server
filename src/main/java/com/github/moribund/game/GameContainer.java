package com.github.moribund.game;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import lombok.val;

import java.util.function.BiConsumer;

public class GameContainer {
    private static final int MAXIMUM_PLAYERS = 2;

    private final Int2ObjectMap<Game> games;
    private static int gameIdCapacity;

    public GameContainer(Int2ObjectMap<Game> games) {
        this.games = games;
    }

    public Game getGame(int gameId) {
        return games.get(gameId);
    }

    public Game getGameForPlayerId(int playerId) {
        for (Game game : games.values()) {
            if (game.containsPlayer(playerId)) {
                return game;
            }
        }
        return null;
    }

    private Game createGame() {
        val game = new Game(gameIdCapacity);
        games.put(gameIdCapacity, game);
        game.setup();
        gameIdCapacity++;
        return game;
    }

    public Game getAvailableGame() {
        for (Game game : games.values()) {
            if (game.getPlayerAmount() < MAXIMUM_PLAYERS) {
                return game;
            }
        }
        return createGame();
    }

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

    void forEachGame(BiConsumer<Integer, Game> gameBiConsumer) {
        games.forEach(gameBiConsumer);
    }
}
