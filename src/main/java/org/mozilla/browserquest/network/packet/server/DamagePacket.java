package org.mozilla.browserquest.network.packet.server;

import org.mozilla.browserquest.network.packet.ClientPacket;
import org.mozilla.browserquest.network.packet.ServerPacket;
import org.vertx.java.core.json.JsonArray;

public class DamagePacket extends ServerPacket {

    private int attacker;
    private int damage;

    public DamagePacket(int attacker, int damage) {
        this.attacker = attacker;
        this.damage = damage;
    }

    @Override
    public String write() {
        JsonArray damagePacket = new JsonArray(new Object[]{ClientPacket.DAMAGE, attacker, damage});
        return damagePacket.encode();
    }
}
