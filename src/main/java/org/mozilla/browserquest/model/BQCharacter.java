package org.mozilla.browserquest.model;

import org.mozilla.browserquest.knownlist.CharacterKnownList;
import org.mozilla.browserquest.knownlist.KnownList;

import java.util.HashMap;
import java.util.Map;

public class BQCharacter extends BQObject {

    private Map<Integer, BQObject> attackers = new HashMap<>();
    private BQObject target;

    private int maxHitPoints;
    private int hitPoints;

    public BQCharacter(int id, String type, String kind, int x, int y) {
        super(id, type, kind, x, y);
    }

    @Override
    protected KnownList newKnownList() {
        return new CharacterKnownList(this);
    }

    public BQObject getTarget() {
        return target;
    }

    public void setTarget(BQObject target) {
        this.target = target;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void addAttacker(BQObject attacker) {
        attackers.put(attacker.getId(), attacker);
    }

    public void removeAttacker(BQObject attacker) {
        attackers.remove(attacker.getId());
    }

    public Map<Integer, BQObject> getAttackers() {
        return attackers;
    }

}
