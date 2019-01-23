package com.github.moribund.net.packets.graphics;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

@Value
public class TimeLeftRefreshPacket implements OutgoingPacket {
    private String displayText;
}
