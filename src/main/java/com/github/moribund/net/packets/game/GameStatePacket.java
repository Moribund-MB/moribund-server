package com.github.moribund.net.packets.game;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class GameStatePacket {
    @Getter
    private final List<Pair<Integer, Pair<Float, Float>>> playerLocations;
    @Getter
    private final List<Pair<Integer, Float>> playerRotations;
}