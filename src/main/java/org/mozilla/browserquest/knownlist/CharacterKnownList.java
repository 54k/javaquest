package org.mozilla.browserquest.knownlist;

import org.mozilla.browserquest.model.BQObject;

public class CharacterKnownList extends ObjectKnownList {

    public CharacterKnownList(BQObject activeObject) {
        super(activeObject);
    }

    @Override
    public int getDistanceToFindObject(BQObject object) {
        return 25;
    }

    @Override
    public int getDistanceToForgetObject(BQObject object) {
        return 30;
    }
}
