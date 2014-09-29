package org.mozilla.browserquest.gameserver.model.actor;

import com.google.common.base.Preconditions;

public enum InstanceType {

    WARRIOR(1, PlayerObject.class),

    // Mobs
    RAT(2, CreatureObject.class),
    SKELETON(3, CreatureObject.class),
    GOBLIN(4, CreatureObject.class),
    OGRE(5, CreatureObject.class),
    SPECTRE(6, CreatureObject.class),
    CRAB(7, CreatureObject.class),
    BAT(8, CreatureObject.class),
    WIZARD(9, CreatureObject.class),
    EYE(10, CreatureObject.class),
    SNAKE(11, CreatureObject.class),
    SKELETON2(12, CreatureObject.class),
    BOSS(13, CreatureObject.class),
    DEATHKNIGHT(14, CreatureObject.class),

    // Armors
    FIREFOX(20, BaseObject.class),
    CLOTHARMOR(21, BaseObject.class),
    LEATHERARMOR(22, BaseObject.class),
    MAILARMOR(23, BaseObject.class),
    PLATEARMOR(24, BaseObject.class),
    REDARMOR(25, BaseObject.class),
    GOLDENARMOR(26, BaseObject.class),

    // Objects
    FLASK(35, ItemObject.class),
    BURGER(36, ItemObject.class),
    CHEST(37, BaseObject.class),
    FIREPOTION(38, ItemObject.class),
    CAKE(39, ItemObject.class),

    // NPCs
    GUARD(40, StaticObject.class),
    KING(41, StaticObject.class),
    OCTOCAT(42, StaticObject.class),
    VILLAGEGIRL(43, StaticObject.class),
    VILLAGER(44, StaticObject.class),
    PRIEST(45, StaticObject.class),
    SCIENTIST(46, StaticObject.class),
    AGENT(47, StaticObject.class),
    RICK(48, StaticObject.class),
    NYAN(49, StaticObject.class),
    SORCERER(50, StaticObject.class),
    BEACHNPC(51, StaticObject.class),
    FORESTNPC(52, StaticObject.class),
    DESERTNPC(53, StaticObject.class),
    LAVANPC(54, StaticObject.class),
    CODER(55, StaticObject.class),

    // Weapons
    SWORD1(60, ItemObject.class),
    SWORD2(61, ItemObject.class),
    REDSWORD(62, ItemObject.class),
    GOLDENSWORD(63, ItemObject.class),
    MORNINGSTAR(64, ItemObject.class),
    AXE(65, ItemObject.class),
    BLUESWORD(66, ItemObject.class);

    private final int id;
    private final Class<? extends BaseObject> prototype;

    InstanceType(int id, Class<? extends BaseObject> prototype) {
        Preconditions.checkNotNull(prototype);
        this.id = id;
        this.prototype = prototype;
    }

    public static InstanceType fromString(String typeName) {
        for (InstanceType type : InstanceType.values()) {
            if (typeName.equalsIgnoreCase(type.name())) {
                return type;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public Class<? extends BaseObject> getPrototype() {
        return prototype;
    }
}
