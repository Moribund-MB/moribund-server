package com.github.moribund.net.packets.game;

import com.github.moribund.net.packets.OutgoingPacket;
import it.unimi.dsi.fastutil.objects.ObjectList;
import javafx.util.Pair;
import lombok.Value;

@Value
public class GameStatePacket implements OutgoingPacket {
    private ObjectList<Pair<Integer, Pair<Float, Float>>> playerLocations;
    private ObjectList<Pair<Integer, Float>> playerRotations;
}