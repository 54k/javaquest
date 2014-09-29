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

    private HashMap<Integer, Class<? extends ClientPacket>> prototypes = new HashMap<>();

    public PacketHandler() {
        addPacketPrototype(ClientPacket.HELLO, EnterWorld.class);
        addPacketPrototype(ClientPacket.MOVE, StartMove.class);
        addPacketPrototype(ClientPacket.ZONE, EnterZone.class);
        addPacketPrototype(ClientPacket.CHAT, SendMessage.class);
        addPacketPrototype(ClientPacket.ATTACK, StartAttack.class);
        addPacketPrototype(ClientPacket.HIT, PlayerAttack.class);
        addPacketPrototype(ClientPacket.HURT, MobAttack.class);
    }

    public void addPacketPrototype(int opcode, Class<? extends ClientPacket> prototype) {
        prototypes.put(opcode, prototype);
    }

    public void handle(NetworkConnectionImpl connection, Object[] packetData) {
        int opcode = (int) packetData[0];
        Class<? extends ClientPacket> prototype = prototypes.get(opcode);

        if (prototype == null) {
            // unknown packet
            //            connection.close();
            return;
        }

        try {
            ClientPacket p = prototype.newInstance();
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
