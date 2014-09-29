package org.mozilla.browserquest.gameserver.model;

import org.mozilla.browserquest.gameserver.model.actor.BQCreature;
import org.mozilla.browserquest.gameserver.model.actor.BQItem;
import org.mozilla.browserquest.gameserver.model.actor.BQObject;
import org.mozilla.browserquest.gameserver.model.actor.BQPlayer;
import org.mozilla.browserquest.gameserver.model.actor.BQStaticObject;

public enum BQType {

    WARRIOR(1, BQPlayer.class),

    // Mobs
    RAT(2, BQCreature.class),
    SKELETON(3, BQCreature.class),
    GOBLIN(4, BQCreature.class),
    OGRE(5, BQCreature.class),
    SPECTRE(6, BQCreature.class),
    CRAB(7, BQCreature.class),
    BAT(8, BQCreature.class),
    WIZARD(9, BQCreature.class),
    EYE(10, BQCreature.class),
    SNAKE(11, BQCreature.class),
    SKELETON2(12, BQCreature.class),
    BOSS(13, BQCreature.class),
    DEATHKNIGHT(14, BQCreature.class),

    // Armors
    FIREFOX(20, BQObject.class),
    CLOTHARMOR(21, BQObject.class),
    LEATHERARMOR(22, BQObject.class),
    MAILARMOR(23, BQObject.class),
    PLATEARMOR(24, BQObject.class),
    REDARMOR(25, BQObject.class),
    GOLDENARMOR(26, BQObject.class),

    // Objects
    FLASK(35, BQItem.class),
    BURGER(36, BQItem.class),
    CHEST(37, BQObject.class),
    FIREPOTION(38, BQItem.class),
    CAKE(39, BQItem.class),

    // NPCs
    GUARD(40, BQStaticObject.class),
    KING(41, BQStaticObject.class),
    OCTOCAT(42, BQStaticObject.class),
    VILLAGEGIRL(43, BQStaticObject.class),
    VILLAGER(44, BQStaticObject.class),
    PRIEST(45, BQStaticObject.class),
    SCIENTIST(46, BQStaticObject.class),
    AGENT(47, BQStaticObject.class),
    RICK(48, BQStaticObject.class),
    NYAN(49, BQStaticObject.class),
    SORCERER(50, BQStaticObject.class),
    BEACHNPC(51, BQStaticObject.class),
    FORESTNPC(52, BQStaticObject.class),
    DESERTNPC(53, BQStaticObject.class),
    LAVANPC(54, BQStaticObject.class),
    CODER(55, BQStaticObject.class),

    // Weapons
    SWORD1(60, BQItem.class),
    SWORD2(61, BQItem.class),
    REDSWORD(62, BQItem.class),
    GOLDENSWORD(63, BQItem.class),
    MORNINGSTAR(64, BQItem.class),
    AXE(65, BQItem.class),
    BLUESWORD(66, BQItem.class);

    private final int id;
    private final Class<? extends BQObject> prototype;

    BQType(int id, Class<? extends BQObject> prototype) {
        this.id = id;
        this.prototype = prototype;
    }

    public static BQType fromString(String typeName) {
        for (BQType type : BQType.values()) {
            if (typeName.equalsIgnoreCase(type.name())) {
                return type;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public Class<? extends BQObject> getPrototype() {
        return prototype;
    }
}
