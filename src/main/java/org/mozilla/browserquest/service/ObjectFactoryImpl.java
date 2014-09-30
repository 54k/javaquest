package org.mozilla.browserquest.service;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import org.mozilla.browserquest.actor.JavassistActorFactory;
import org.mozilla.browserquest.gameserver.model.actor.BaseObject;
import org.mozilla.browserquest.gameserver.service.WorldService;

public class ObjectFactoryImpl implements ObjectFactory {

    @Inject
    private WorldService worldService;
    @Inject
    private IdFactory idFactory;

    private JavassistActorFactory delegate = new JavassistActorFactory();

    @Override
    public <T extends BaseObject> T createObject(Class<T> objectPrototype) {
        T object = delegate.newActor(objectPrototype);
        object.setId(idFactory.getNextId());
        worldService.addObject(object);
        return object;
    }

    @Override
    public <T extends BaseObject> void destroyObject(T object) {
        Preconditions.checkNotNull(object);
        worldService.removeObject(object);
        idFactory.releaseId(object.getId());
    }
}
