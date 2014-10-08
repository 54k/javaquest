package org.mozilla.browserquest.space;

import org.mozilla.browserquest.actor.Component;
import org.mozilla.browserquest.actor.ComponentPrototype;

@ComponentPrototype(AppSpaceEventListener.class)
public class MapInstanceSpaceComponent extends Component<MapInstance> implements AppSpaceEventListener {

    private AppSpace appSpace;

    @Override
    public void onAppSpaceCreated(AppSpace appSpace) {
        this.appSpace = appSpace;
    }

    @Override
    public void onAppSpaceTicked() {

    }
}
