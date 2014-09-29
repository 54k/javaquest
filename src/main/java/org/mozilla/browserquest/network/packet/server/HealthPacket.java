package org.mozilla.browserquest.network.packet.server;

import org.mozilla.browserquest.gameserver.model.actor.CharacterObject;
import org.mozilla.browserquest.network.packet.ClientPacket;
import org.mozilla.browserquest.network.packet.ServerPacket;
import org.vertx.java.core.json.JsonArray;

public class HealthPacket extends ServerPacket {

    private CharacterObject character;

    public HealthPacket(CharacterObject character) {
        this.character = character;
    }

    @Override
    public String write() {
        return new JsonArray(new Object[]{ClientPacket.HP, character.getStatusController().getHitPoints()}).encode();
    }
}
