package org.mozilla.browserquest.gameserver.model.creature;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.gameserver.model.BQType;
import org.mozilla.browserquest.gameserver.model.Orientation;
import org.mozilla.browserquest.gameserver.model.actor.BQCreature;
import org.mozilla.browserquest.gameserver.model.actor.BQObject;
import org.mozilla.browserquest.gameserver.model.position.PositionController;
import org.mozilla.browserquest.service.ObjectFactory;
import org.mozilla.browserquest.util.PositionUtil;

import java.util.Map;
import java.util.Map.Entry;

@ComponentPrototype(DropController.class)
public class DropControllerComponent extends Component<BQCreature> implements DropController {

    @LazyInject
    private ObjectFactory objectFactory;

    @Override
    public void drop() {
        BQCreature actor = getActor();
        Map<String, Integer> drops = actor.getTemplate().getDrops();
        PositionController positionController = actor.getPositionController();
        for (Entry<String, Integer> dropEntry : drops.entrySet()) {
            BQObject dropItem = createDropItem(dropEntry.getKey());
            PositionController positionController1 = dropItem.getPositionController();
            positionController1.setWorld(positionController.getWorld());
            positionController1.setPosition(PositionUtil.getRandomPositionNear(actor));
            positionController1.setOrientation(Orientation.BOTTOM);
            positionController1.spawn();
        }
    }

    private BQObject createDropItem(String typeName) {
        BQType type = BQType.fromString(typeName);
        BQObject object = objectFactory.createObject(type.getPrototype());
        object.setType(type);
        return object;
    }
}
