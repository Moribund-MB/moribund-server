package com.github.moribund.entity;

import com.esotericsoftware.kryonet.Connection;

public interface PlayableCharacter {
    Tile getCurrentTile();
    int getPlayerId();
    Connection getConnection();
}
