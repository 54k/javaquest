package org.mozilla.browserquest.actor;

public interface ActorFactory {

    <T extends Actor> T newActor(Class<T> actorPrototype);
}
