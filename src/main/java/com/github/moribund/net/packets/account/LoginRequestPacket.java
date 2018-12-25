package com.github.moribund.net.packets.account;

/**
 * The request sent by the client to the server that a person is making
 * an account, so it must register that request and follow instructions
 * to handle the player server-sided.
 */
public class LoginRequestPacket {
    /**
     * A private constructor to ensure the server cannot unexpectedly send this
     * request to the client.
     */
    private LoginRequestPacket() { }
}
