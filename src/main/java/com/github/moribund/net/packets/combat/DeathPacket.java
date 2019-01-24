package com.github.moribund.net.packets.combat;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

@Value
public class DeathPacket implements OutgoingPacket {
    private int playerId;
}
