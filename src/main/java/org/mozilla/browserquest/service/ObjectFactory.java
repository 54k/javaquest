package org.mozilla.browserquest.service;

import org.mozilla.browserquest.gameserver.model.actor.BQObject;

public interface ObjectFactory {

    <T extends BQObject> T createObject(Class<T> objectPrototype);

    <T extends BQObject> void destroyObject(T object);
}
