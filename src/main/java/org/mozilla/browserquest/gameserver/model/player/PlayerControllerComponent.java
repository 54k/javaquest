package org.mozilla.browserquest.gameserver.model.player;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.CharacterObject;
import org.mozilla.browserquest.gameserver.model.actor.PlayerObject;
import org.mozilla.browserquest.gameserver.model.combat.CombatEventListener;
import org.mozilla.browserquest.gameserver.model.knownlist.KnownListEventListener;
import org.mozilla.browserquest.gameserver.model.position.PositionController;
import org.mozilla.browserquest.gameserver.model.status.StatusController;
import org.mozilla.browserquest.gameserver.model.status.StatusEventListener;
import org.mozilla.browserquest.network.NetworkConnection;
import org.mozilla.browserquest.network.packet.server.DamagePacket;
import org.mozilla.browserquest.network.packet.server.DespawnPacket;
import org.mozilla.browserquest.network.packet.server.HealthPacket;
import org.mozilla.browserquest.network.packet.server.SpawnPacket;

@ComponentPrototype(PlayerController.class)
public class PlayerControllerComponent extends Component<PlayerObject> implements PlayerController, KnownListEventListener, CombatEventListener, StatusEventListener {

    @Override
    public void onObjectAddedToKnownList(BaseObject object) {
        getActor().getConnection().write(new SpawnPacket(object));
    }

    @Override
    public void onObjectRemovedFromKnownList(BaseObject object) {
        getActor().getConnection().write(new DespawnPacket(object.getId()));
    }

    @Override
    public void onAttack(CharacterObject attacker, int damage) {
        StatusController statusController = getActor().getStatusController();
        if (statusController.isDead()) {
            return;
        }

        statusController.damage(attacker, damage);
    }

    @Override
    public void onHeal(CharacterObject healer, int amount) {
    }

    @Override
    public void onDamage(CharacterObject attacker, int amount) {
        NetworkConnection connection = getActor().getConnection();
        connection.write(new DamagePacket(attacker.getId(), amount));
        connection.write(new HealthPacket(getActor()));
    }

    @Override
    public void onRevive() {
    }

    @Override
    public void onDie(CharacterObject killer) {
        PositionController positionController = getActor().getPositionController();
        positionController.decay();
        getActor().getConnection().write(new DespawnPacket(getActor().getId()));
    }
}
