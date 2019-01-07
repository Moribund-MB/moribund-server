package com.github.moribund.game;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.val;

import java.util.function.BiConsumer;

public class GameContainer {
    private static final int MAXIMUM_PLAYERS = 2;

    private final Int2ObjectMap<Game> games;
    private static int gameId;

    public GameContainer(Int2ObjectMap<Game> games) {
        this.games = games;
    }

    public Game getGame(int gameId) {
        return games.get(gameId);
    }

    private Game createGame() {
        val game = new Game(gameId, new Int2ObjectOpenHashMap<>());
        games.put(gameId, game);
        gameId++;
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

    void forEachGame(BiConsumer<Integer, Game> gameBiConsumer) {
        games.forEach(gameBiConsumer);
    }
}
