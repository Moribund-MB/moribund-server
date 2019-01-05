package com.github.moribund.net.packets.login;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

@Value
public class LoginResponsePacket implements OutgoingPacket {
    private LoginResponse loginResponse;
}
