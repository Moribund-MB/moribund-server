package net.packets;

import lombok.Data;

@Data
public class MessagePacket {

    private final String name;
    private final String message;

    public MessagePacket(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
