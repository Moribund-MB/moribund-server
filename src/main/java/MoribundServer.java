import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import net.TextListener;
import net.packets.MessagePacket;

import java.io.IOException;

public class MoribundServer {
    public static void main(String[] args) {
        Server server = new Server();
        server.addListener(new TextListener());
        registerPackets(server.getKryo());

        server.start();

        try {
            server.bind(43594);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void registerPackets(Kryo kryo) {
        kryo.register(MessagePacket.class);
    }
}