package com.github.moribund.objects.playable;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.graphics.AnimationProjectilePacket;
import com.github.moribund.objects.nonplayable.ProjectileType;
import com.github.moribund.objects.playable.containers.Equipment;
import com.github.moribund.objects.playable.containers.Inventory;
import com.github.moribund.objects.playable.containers.ItemContainer;
import com.github.moribund.utils.ArtificialTime;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

/**
 * The {@code Player} that represents somebody being controlled by the client
 * of its respective {@link Connection}.
 */
public final class Player implements PlayableCharacter {

    @Getter
    private final int gameId;
    /**
     * The unique player ID based on the {@link com.esotericsoftware.kryonet.Connection} of
     * the client to the server.
     */
    @Getter
    private final int playerId;
    @Getter
    private final ItemContainer inventory;
    @Getter
    private final ItemContainer equipment;
    @Getter @Setter
    private float x;
    @Getter @Setter
    private float y;
    @Getter @Setter
    private float rotation;
    @Getter
    private int hitpoints = 100;
    @Getter
    private ArtificialTime timeLeft;
    /**
     * The connection of the server between the client.
     */
    @Getter @Setter
    private Connection connection;

    /**
     * Makes a {@code Player} with its unique player ID and provides the spawn
     * tile the {@code Player} starts at.
     * @param playerId The unique player ID.
     */
    public Player(int gameId, int playerId, float startingX, float startingY, ArtificialTime initialTimeLeft) {
        this.gameId = gameId;
        this.playerId = playerId;
        x = startingX;
        y = startingY;
        timeLeft = initialTimeLeft;
        inventory = new Inventory();
        equipment = new Equipment();
    }

    public void collide(ProjectileType projectileType) {
        switch (projectileType) {
            case ARROW:
                hitpoints -= 10;
                break;
        }
    }

    @Override
    public boolean canAttack() {
        return true;
    }

    @Override
    public void attack() {
        val animationProjectilePacket = new AnimationProjectilePacket(playerId, 0, 0, 15);
        MoribundServer.getInstance().getGameContainer().getGame(gameId).queuePacket(animationProjectilePacket);
    }
}
