package com.github.moribund.net.packets;

import com.esotericsoftware.kryonet.Connection;

/**
 * An {@code IncomingPacket} is a packet that is received by the server. This allows the client to follow the
 * <a href="https://en.wikipedia.org/wiki/Single_responsibility_principle">Single-Responsibility Principle</a>
 * as now each packet is responsible for handling itself rather than a {@link com.esotericsoftware.kryonet.Listener}
 * being omnipotent and handling everything.
 */
public interface IncomingPacket {
    /**
     * Processes what to do when the packet is received.
     * @param connection The connection between the server and the client.
     */
    void process(Connection connection);
}
