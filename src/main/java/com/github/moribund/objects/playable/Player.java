package com.github.moribund.objects.playable;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.game.data.AttackableItemsParser;
import com.github.moribund.game.data.WeaponDefinitionsParser;
import com.github.moribund.net.packets.combat.DeathPacket;
import com.github.moribund.net.packets.graphics.AnimationProjectilePacket;
import com.github.moribund.objects.nonplayable.ItemType;
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
    @Getter
    private final String username;
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
    public Player(int gameId, int playerId, String username, float startingX, float startingY, ArtificialTime initialTimeLeft) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.username = username;
        x = startingX;
        y = startingY;
        timeLeft = initialTimeLeft;
        inventory = new Inventory();
        equipment = new Equipment();

        setTimerChecks();
    }

    private void setTimerChecks() {
        timeLeft.setTimeChecker(time -> {
            if (time == 0) {
                sendDeath(null);
            }
        });
    }

    public void collide(ProjectileType projectileType, PlayableCharacter source) {
        switch (projectileType) {
            case ARROW:
                damage(10, source);
                break;
        }
    }

    @Override
    public void damage(int damageAmount, PlayableCharacter source) {
        hitpoints -= damageAmount;
        if (hitpoints <= 0) {
            sendDeath(source);
        }
    }

    private void sendDeath(PlayableCharacter source) {
        val deathPacket = new DeathPacket(playerId);
        connection.sendTCP(deathPacket);

        if (source != null) {
            long timeGained = (long) (timeLeft.getTime() * 0.2);
            source.getTimeLeft().incrementTime(timeGained);
        }
        MoribundServer.getInstance().getGameContainer().getGame(gameId).removePlayer(playerId);
    }

    @Override
    public boolean canAttack() {
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);
        if (game == null || !game.isStarted() || game.isFinished()) {
            return false;
        }
        for (int itemId : equipment.getItemIds()) {
            if (AttackableItemsParser.isAttackableItem(itemId)) {
                if (itemId == ItemType.BOW.getId()) {
                    // todo see if this can be improved by not hard-coding this.
                    return equipment.getItemIds().contains(ItemType.ARROW.getId());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void attack() {
        val weaponId = equipment.getItemIds().get(0); // albeit this can be arrows, I decided to make arrows have
                                                      // the same definitions as a bow. There are too many exceptions
                                                      // for arrows in this code.
        val weaponDefinition = WeaponDefinitionsParser.getWeaponDefinition(weaponId);
        if (weaponDefinition != null) {
            val animationProjectilePacket = new AnimationProjectilePacket(playerId, weaponDefinition.getAnimationId(),
                    weaponDefinition.getProjectileId(), weaponDefinition.getProjectileMovementSpeed());
            MoribundServer.getInstance().getGameContainer().getGame(gameId).queuePacket(animationProjectilePacket);
        }
    }
}
