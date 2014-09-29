package org.mozilla.browserquest.gameserver.model.actor;

import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.gameserver.model.Spawn;
import org.mozilla.browserquest.gameserver.model.creature.CreatureControllerComponent;
import org.mozilla.browserquest.gameserver.model.creature.CreatureStatsCalculatorComponent;
import org.mozilla.browserquest.gameserver.model.creature.DropControllerComponent;
import org.mozilla.browserquest.template.CreatureTemplate;

@ActorPrototype({CreatureControllerComponent.class, CreatureStatsCalculatorComponent.class, DropControllerComponent.class})
public abstract class CreatureObject extends CharacterObject implements CreatureObjectView {

    private CreatureTemplate template;
    private Spawn spawn;

    public CreatureTemplate getTemplate() {
        return template;
    }

    public void setTemplate(CreatureTemplate template) {
        this.template = template;
        getInventoryController().setWeapon(template.getWeapon());
        getInventoryController().setArmor(template.getArmor());
    }

    public Spawn getSpawn() {
        return spawn;
    }

    public void setSpawn(Spawn spawn) {
        this.spawn = spawn;
    }
}
