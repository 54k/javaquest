package org.mozilla.browserquest.gameserver.model.creature;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;
import org.mozilla.browserquest.gameserver.model.Orientation;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.model.actor.CreatureObject;
import org.mozilla.browserquest.gameserver.model.actor.InstanceType;
import org.mozilla.browserquest.gameserver.model.position.PositionController;
import org.mozilla.browserquest.inject.LazyInject;
import org.mozilla.browserquest.service.ObjectFactory;
import org.mozilla.browserquest.util.PositionUtil;

import java.util.Map;
import java.util.Map.Entry;

@ComponentPrototype(DropController.class)
public class DropControllerComponent extends Component<CreatureObject> implements DropController {

    @LazyInject
    private ObjectFactory objectFactory;

    @Override
    public void drop() {
        CreatureObject actor = getActor();
        Map<String, Integer> drops = actor.getTemplate().getDrops();
        PositionController positionController = actor.getPositionController();
        for (Entry<String, Integer> dropEntry : drops.entrySet()) {
            BaseObject dropItem = createDropItem(dropEntry.getKey());
            PositionController positionController1 = dropItem.getPositionController();
            positionController1.setWorldMapInstance(positionController.getWorldMapInstance());
            positionController1.setPosition(PositionUtil.getRandomPositionNear(actor));
            positionController1.setOrientation(Orientation.BOTTOM);
            positionController1.spawn();
        }
    }

    private BaseObject createDropItem(String typeName) {
        InstanceType type = InstanceType.fromString(typeName);
        BaseObject object = objectFactory.createObject(type.getPrototype());
        object.setInstanceType(type);
        return object;
    }
}
