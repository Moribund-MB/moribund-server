package com.github.moribund.net.packets.game;

import com.github.moribund.net.packets.OutgoingPacket;
import it.unimi.dsi.fastutil.objects.ObjectList;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class GameStatePacket implements OutgoingPacket {
    @Getter
    private final ObjectList<Pair<Integer, Pair<Float, Float>>> playerLocations;
    @Getter
    private final ObjectList<Pair<Integer, Float>> playerRotations;
}