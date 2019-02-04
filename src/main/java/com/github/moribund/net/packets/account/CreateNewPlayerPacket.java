package com.github.moribund.net.packets.account;

import com.github.moribund.net.packets.OutgoingPacket;
import com.github.moribund.net.packets.data.GroundItemData;
import com.github.moribund.net.packets.data.PlayerData;
import com.github.moribund.objects.playable.players.PlayableCharacter;
import com.github.moribund.objects.playable.players.Player;
import it.unimi.dsi.fastutil.objects.ObjectList;
import lombok.Value;

/**
 * The response from the server that a {@link Player}
 * has logged in. This makes the client do instructions by this message's
 * arrival.
 */
@Value
public class CreateNewPlayerPacket implements OutgoingPacket {
    /**
     * The game ID of the newly made player.
     */
    private int gameId;

    /**
     * The unique player ID of the one who just logged in.
     */
    private int playerId;

    /**
     * The {@link PlayerData} of all the {@link PlayableCharacter}s in the
     * game.
     */
    private ObjectList<PlayerData> playerData;

    /**
     * The {@link GroundItemData} of all the {@link com.github.moribund.objects.nonplayable.GroundItem}s in the game.
     */
    private ObjectList<GroundItemData> groundItems;
}