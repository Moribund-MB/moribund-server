package com.github.moribund.net.packets.graphics;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

/**
 * The packet used to send text to the death timer.
 */
@Value
public class TimeLeftRefreshPacket implements OutgoingPacket {
    /**
     * The display text to display for the lobby timer.
     */
    private String displayText;
}
