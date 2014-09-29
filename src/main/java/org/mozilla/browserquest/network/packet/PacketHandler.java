package org.mozilla.browserquest.network.packet;

import org.mozilla.browserquest.network.NetworkConnectionImpl;
import org.mozilla.browserquest.network.packet.client.EnterWorld;
import org.mozilla.browserquest.network.packet.client.EnterZone;
import org.mozilla.browserquest.network.packet.client.MobAttack;
import org.mozilla.browserquest.network.packet.client.PlayerAttack;
import org.mozilla.browserquest.network.packet.client.SendMessage;
import org.mozilla.browserquest.network.packet.client.StartAttack;
import org.mozilla.browserquest.network.packet.client.StartMove;

import java.util.HashMap;

public class PacketHandler {

    private HashMap<Integer, Class<? extends Packet>> prototypes = new HashMap<>();

    public PacketHandler() {
        addPacketPrototype(Packet.HELLO, EnterWorld.class);
        addPacketPrototype(Packet.MOVE, StartMove.class);
        addPacketPrototype(Packet.ZONE, EnterZone.class);
        addPacketPrototype(Packet.CHAT, SendMessage.class);
        addPacketPrototype(Packet.ATTACK, StartAttack.class);
        addPacketPrototype(Packet.HIT, PlayerAttack.class);
        addPacketPrototype(Packet.HURT, MobAttack.class);
    }

    public void addPacketPrototype(int opcode, Class<? extends Packet> prototype) {
        prototypes.put(opcode, prototype);
    }

    public void handle(NetworkConnectionImpl connection, Object[] packetData) {
        int opcode = (int) packetData[0];
        Class<? extends Packet> prototype = prototypes.get(opcode);

        if (prototype == null) {
            // unknown packet
            //            connection.close();
            return;
        }

        try {
            Packet p = prototype.newInstance();
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
