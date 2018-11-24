package com.github.moribund.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.github.moribund.MoribundServer;
import com.github.moribund.entity.Tile;
import com.github.moribund.entity.PlayableCharacter;
import com.github.moribund.entity.Player;
import com.github.moribund.net.packets.DrawNewPlayerPacket;
import com.github.moribund.net.packets.LoginPacket;
import com.github.moribund.net.packets.LoginRequestPacket;
import javafx.util.Pair;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AccountListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof LoginRequestPacket) {
            createAccount(connection);
        }
    }

    private void createAccount(Connection connection) {
        val playersMap = MoribundServer.getInstance().getPlayers();
        val playerId = connection.getID();
        makeNewPlayer(playerId, connection);

        val player = playersMap.get(playerId);
        sendNewPlayerPacket(player);
        sendPlayersToNewPlayer(player);
    }

    private void sendPlayersToNewPlayer(PlayableCharacter player) {
        // note this includes the newly made player
        val playersMap = MoribundServer.getInstance().getPlayers();
        List<Pair<Integer, Tile>> playerTiles = new ArrayList<>();
        playersMap.forEach((playerId, aPlayer) ->
                playerTiles.add(new Pair<>(playerId, aPlayer.getCurrentTile())));

        val loginPacket = new LoginPacket(player.getPlayerId(), playerTiles);
        player.getConnection().sendTCP(loginPacket);
    }

    private void sendNewPlayerPacket(PlayableCharacter newPlayer) {
        val playersMap = MoribundServer.getInstance().getPlayers();
        val newPlayerLoginPacket = new DrawNewPlayerPacket(newPlayer.getPlayerId(), newPlayer.getCurrentTile());
        playersMap.forEach((playerId, player) -> player.getConnection().sendTCP(newPlayerLoginPacket));
    }

    private void makeNewPlayer(int playerId, Connection connection) {
        val player = new Player(playerId, new Tile(ThreadLocalRandom.current().nextInt(0, 100),
                ThreadLocalRandom.current().nextInt(0, 100)));
        player.setConnection(connection);
        val playersMap = MoribundServer.getInstance().getPlayers();
        playersMap.putIfAbsent(playerId, player);
    }
}