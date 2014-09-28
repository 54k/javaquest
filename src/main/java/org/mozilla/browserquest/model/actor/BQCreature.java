package org.mozilla.browserquest.model.actor;

import org.mozilla.browserquest.actor.ActorPrototype;
import org.mozilla.browserquest.model.BQSpawn;
import org.mozilla.browserquest.model.creature.CreatureControllerComponent;
import org.mozilla.browserquest.model.creature.CreatureStatsCalculatorComponent;
import org.mozilla.browserquest.template.CreatureTemplate;

@ActorPrototype({CreatureControllerComponent.class, CreatureStatsCalculatorComponent.class})
public abstract class BQCreature extends BQCharacter {

    private CreatureTemplate template;
    private BQSpawn spawn;

    public CreatureTemplate getTemplate() {
        return template;
    }

    public void setTemplate(CreatureTemplate template) {
        this.template = template;
        getInventoryController().setWeapon(template.getWeapon());
        getInventoryController().setArmor(template.getArmor());
    }


    public BQSpawn getSpawn() {
        return spawn;
    }

    public void setSpawn(BQSpawn spawn) {
        this.spawn = spawn;
    }
}
