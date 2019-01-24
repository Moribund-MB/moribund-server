package com.github.moribund.net.packets.graphics;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

@Value
public class LobbyTimeLeftRefreshPacket implements OutgoingPacket {
    private String display;
}
