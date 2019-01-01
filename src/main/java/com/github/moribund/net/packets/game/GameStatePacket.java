package com.github.moribund.net.packets.game;

import com.github.moribund.net.packets.OutgoingPacket;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class GameStatePacket implements OutgoingPacket {
    @Getter
    private final List<Pair<Integer, Pair<Float, Float>>> playerLocations;
    @Getter
    private final List<Pair<Integer, Float>> playerRotations;
}