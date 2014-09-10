package org.mozilla.browserquest.network.packet;

import com.google.inject.Injector;
import org.mozilla.browserquest.network.DefaultNetworkConnection;
import org.mozilla.browserquest.network.packet.client.ChatPacket;
import org.mozilla.browserquest.network.packet.client.HelloPacket;
import org.mozilla.browserquest.network.packet.client.MovePacket;
import org.mozilla.browserquest.network.packet.client.ZonePacket;

import java.util.HashMap;

public class PacketHandler {

    private HashMap<Integer, Class<? extends Packet>> prototypes = new HashMap<>();
    private Injector injector;

    public PacketHandler(Injector injector) {
        this.injector = injector;

        addPacketPrototype(Packet.HELLO, HelloPacket.class);
        addPacketPrototype(Packet.MOVE, MovePacket.class);
        addPacketPrototype(Packet.ZONE, ZonePacket.class);
        addPacketPrototype(Packet.CHAT, ChatPacket.class);
    }

    public void addPacketPrototype(int opcode, Class<? extends Packet> prototype) {
        prototypes.put(opcode, prototype);
    }

    public void handle(DefaultNetworkConnection connection, Object[] packetData) {
        int opcode = (int) packetData[0];
        Class<? extends Packet> prototype = prototypes.get(opcode);

        if (prototype == null) {
            // unknown packet
            connection.close();
            return;
        }

        try {
            Packet p = prototype.newInstance();
            injector.injectMembers(p);
            p.setConnection(connection);
            Object[] data = new Object[packetData.length - 1];
            System.arraycopy(packetData, 1, data, 0, data.length);
            p.setData(data);
            p.run();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
