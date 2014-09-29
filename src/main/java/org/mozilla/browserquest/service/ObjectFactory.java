package org.mozilla.browserquest.service;

import org.mozilla.browserquest.gameserver.model.actor.BaseObject;

public interface ObjectFactory {

    <T extends BaseObject> T createObject(Class<T> objectPrototype);

    <T extends BaseObject> void destroyObject(T object);
}
