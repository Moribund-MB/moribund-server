package com.github.moribund.net.packets.graphics;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

@Value
public class UpdateAppearancePacket implements OutgoingPacket {
    private int playerId;
}
