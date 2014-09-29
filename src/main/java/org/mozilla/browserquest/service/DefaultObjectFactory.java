package org.mozilla.browserquest.service;

import com.google.inject.Inject;
import org.mozilla.browserquest.actor.JavassistActorFactory;
import org.mozilla.browserquest.gameserver.model.actor.BQObject;

public class DefaultObjectFactory implements ObjectFactory {

    private JavassistActorFactory delegate = new JavassistActorFactory();

    @Inject
    private IdFactory idFactory;

    @Override
    public <T extends BQObject> T createObject(Class<T> objectPrototype) {
        T object = delegate.newActor(objectPrototype);
        object.setId(idFactory.getNextId());
        return object;
    }

    @Override
    public <T extends BQObject> void destroyObject(T object) {
        idFactory.releaseId(object.getId());
    }
}
