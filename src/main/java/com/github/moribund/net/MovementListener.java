package com.github.moribund.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.github.moribund.MoribundServer;
import com.github.moribund.entity.Tile;
import com.github.moribund.net.packets.TilePacket;
import lombok.val;

public class MovementListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof TilePacket) {
            val tilePacket = (TilePacket) object;
            val playerId = tilePacket.getPlayerId();
            val tile = tilePacket.getTile();
            setTileForPlayer(playerId, tile);
        }
    }

    private void setTileForPlayer(int playerId, Tile tile) {
        val player = MoribundServer.getInstance().getPlayers().get(playerId);
        player.setTile(tile);
    }
}
