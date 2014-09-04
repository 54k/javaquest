package org.mozilla.browserquest.network;

import org.mozilla.browserquest.network.packet.HelloPacket;

import java.util.HashMap;

public class PacketHandler {

    private HashMap<Integer, Class<? extends Packet>> prototypes;

    public PacketHandler() {
        addPacketPrototype(Packet.HELLO, HelloPacket.class);
    }

    public void addPacketPrototype(int opcode, Class<? extends Packet> prototype) {
        prototypes.put(opcode, prototype);
    }

    public void handle(WebSocketNetworkConnection connection, Object[] packetData) {
        int opcode = (int) packetData[0];
        Class<? extends Packet> prototype = prototypes.get(opcode);

        if (prototype == null) {
            // unknown packet
            connection.close();
            return;
        }

        try {
            Packet p = prototype.newInstance();
            p.setConnection(connection);
            p.setData(packetData);
            p.run();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
