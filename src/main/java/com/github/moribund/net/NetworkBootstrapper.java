package com.github.moribund.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryonet.Server;
import com.github.moribund.net.packets.account.DrawNewPlayerPacket;
import com.github.moribund.net.packets.account.LoginPacket;
import com.github.moribund.net.packets.account.LoginRequestPacket;
import com.github.moribund.net.packets.account.LogoutPacket;
import com.github.moribund.net.packets.game.GameStatePacket;
import com.github.moribund.net.packets.key.KeyPressedPacket;
import com.github.moribund.net.packets.key.KeyPressedResponsePacket;
import com.github.moribund.net.packets.key.KeyUnpressedPacket;
import com.github.moribund.net.packets.key.KeyUnpressedResponsePacket;
import com.github.moribund.net.packets.movement.LocationPacket;
import com.github.moribund.net.packets.movement.RotationPacket;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The {@code NetworkBootstrapper} class is responsible for giving the
 * initial instructions to start the networking process and listeners.
 */
public class NetworkBootstrapper {

    private static final int PORT = 43594;

    /**
     * Connects to the {@link com.esotericsoftware.kryonet.Client} using our
     * {@link Server}. This method registers the packets before starting the
     * {@link com.esotericsoftware.kryonet.Connection}.
     */
    public void connect() {
        Server server = new Server();
        server.addListener(new ServerListener());
        registerPackets(server.getKryo());

        server.start();

        try {
            server.bind(PORT, PORT);
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
        kryo.register(RotationPacket.class);
        kryo.register(GameStatePacket.class);
        kryo.register(LogoutPacket.class);
    }
}
