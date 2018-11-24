package com.github.moribund.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.github.moribund.MoribundServer;
import com.github.moribund.entity.Coordinate;
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
        List<Pair<Integer, Pair<Integer, Integer>>> listOfPlayerCoordinates = new ArrayList<>();
        playersMap.forEach((playerId, aPlayer) -> {
            listOfPlayerCoordinates.add(new Pair<>(playerId, aPlayer.getCurrentCoordinate().toPair()));
        });
        val loginPacket = new LoginPacket(player.getPlayerId(), listOfPlayerCoordinates);
        player.getConnection().sendTCP(loginPacket);
    }

    private void sendNewPlayerPacket(PlayableCharacter newPlayer) {
        val playersMap = MoribundServer.getInstance().getPlayers();
        val newPlayerLoginPacket = new DrawNewPlayerPacket(newPlayer.getPlayerId(),
                newPlayer.getCurrentCoordinate().getX(), newPlayer.getCurrentCoordinate().getY());
        playersMap.forEach((playerId, player) -> {
            player.getConnection().sendTCP(newPlayerLoginPacket);
        });
    }

    private void makeNewPlayer(int playerId, Connection connection) {
        val player = new Player(playerId, new Coordinate(ThreadLocalRandom.current().nextInt(0, 100),
                ThreadLocalRandom.current().nextInt(0, 100)));
        player.setConnection(connection);
        val playersMap = MoribundServer.getInstance().getPlayers();
        playersMap.putIfAbsent(playerId, player);
    }
}