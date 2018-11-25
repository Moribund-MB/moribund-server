package com.github.moribund.entity;

import com.esotericsoftware.kryonet.Connection;

public interface PlayableCharacter {
    Tile getCurrentTile();
    void setTile(Tile tile);
    int getPlayerId();
    Connection getConnection();
}
