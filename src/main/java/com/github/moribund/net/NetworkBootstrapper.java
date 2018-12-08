package com.github.moribund.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryonet.Server;
import com.github.moribund.net.packets.*;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The {@code NetworkBootstrapper} class is responsible for giving the
 * initial instructions to start the networking process and listeners.
 */
public class NetworkBootstrapper {

    /**
     * Connects to the {@link com.esotericsoftware.kryonet.Client} using our
     * {@link Server}. This method registers the packets before starting the
     * {@link com.esotericsoftware.kryonet.Connection}.
     */
    public void connect() {
        Server server = new Server();
        server.addListener(new MovementListener());
        server.addListener(new AccountListener());
        server.addListener(new KeyListener());
        registerPackets(server.getKryo());

        server.start();

        try {
            server.bind(43594);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registers packets that are serialized by {@link Kryo}. Packets
     * are NOT required to implement {@link Kryo} or {@link com.esotericsoftware.kryo.KryoSerializable}.
     * @param kryo The {@link Server}'s {@link Kryo}.
     */
    private void registerPackets(Kryo kryo) {
        kryo.register(DrawNewPlayerPacket.class);
        kryo.register(LoginPacket.class);
        kryo.register(LoginRequestPacket.class);
        kryo.register(ArrayList.class, new JavaSerializer());
        kryo.register(Pair.class, new JavaSerializer());
        kryo.register(Integer.class, new JavaSerializer());
        kryo.register(KeyPressedPacket.class);
        kryo.register(KeyPressedResponsePacket.class);
        kryo.register(KeyUnpressedPacket.class);
        kryo.register(KeyUnpressedResponsePacket.class);
        kryo.register(LocationPacket.class);
    }
}
