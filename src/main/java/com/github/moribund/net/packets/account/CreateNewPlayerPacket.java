package com.github.moribund.net.packets.account;

import com.github.moribund.net.packets.OutgoingPacket;
import com.github.moribund.net.packets.data.GroundItemData;
import com.github.moribund.net.packets.data.PlayerLocationData;
import com.github.moribund.net.packets.data.PlayerRotationData;
import com.github.moribund.objects.playable.Player;
import it.unimi.dsi.fastutil.objects.ObjectList;
import lombok.Value;

/**
 * The response from the server that a {@link Player}
 * has logged in. This makes the client do instructions by this message's
 * arrival.
 */
@Value
public class CreateNewPlayerPacket implements OutgoingPacket {
    private int gameId;
    /**
     * The unique player ID of the one who just logged in.
     */
    private int playerId;

    /**
     * The locations of all the {@link Player}s in the
     * game currently so that they may be rendered to this player logging in.
     */
    private ObjectList<PlayerLocationData> playerLocations;

    /**
     * The rotations of all the {@link Player}s in the
     * game currently so that they may be rendered to this player logging in.
     */
    private ObjectList<PlayerRotationData> playerRotations;

    private ObjectList<GroundItemData> groundItems;
}