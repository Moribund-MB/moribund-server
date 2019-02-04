package com.github.moribund.net.packets.login;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

/**
 * The {@code LoginResponsePacket} handles responses by the login response.
 */
@Value
public class LoginResponsePacket implements OutgoingPacket {
    /**
     * The {@link LoginResponse} by the server to the {@link LoginPacket}.
     */
    private LoginResponse loginResponse;
}
