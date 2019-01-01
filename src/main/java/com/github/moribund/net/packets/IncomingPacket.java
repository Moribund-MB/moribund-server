package com.github.moribund.net.packets;

import com.esotericsoftware.kryonet.Connection;

public interface IncomingPacket {
    void process(Connection connection);
}
