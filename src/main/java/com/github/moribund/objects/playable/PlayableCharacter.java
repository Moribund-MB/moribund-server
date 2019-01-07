package com.github.moribund.objects.playable;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.objects.attributes.Movable;

/**
 * The {@code PlayableCharacter} interface is a template
 * for a character that can be interacted with by keys. All
 * playable characters are assumed as visible and movable.
 */
public interface PlayableCharacter extends Movable {

    int getGameId();
    /**
     * Gets the player's unique ID generated by the connection.
     * @return The Player's unique ID.
     */
    int getPlayerId();

    /**
     * Gets the connection of the server between the client.
     * @return The connection of the server between the client.
     */
    Connection getConnection();
}