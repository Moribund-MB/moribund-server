package com.github.moribund.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryonet.Server;
import com.github.moribund.net.packets.DrawNewPlayerPacket;
import com.github.moribund.net.packets.LoginPacket;
import com.github.moribund.net.packets.LoginRequestPacket;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;

public class NetworkBootstrapper {

    public void connect() {
        Server server = new Server();
        server.addListener(new MovementListener());
        server.addListener(new AccountListener());
        registerPackets(server.getKryo());

        server.start();

        try {
            server.bind(43594);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerPackets(Kryo kryo) {
        kryo.register(DrawNewPlayerPacket.class);
        kryo.register(LoginPacket.class);
        kryo.register(LoginRequestPacket.class);
        kryo.register(ArrayList.class, new JavaSerializer());
        kryo.register(Pair.class, new JavaSerializer());
        kryo.register(Integer.class, new JavaSerializer());
    }
}
