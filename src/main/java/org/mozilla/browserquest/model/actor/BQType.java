package org.mozilla.browserquest.model.actor;

public enum BQType {

    WARRIOR(1, BQPlayer.class),

    // Mobs
    RAT(2, BQCharacter.class),
    SKELETON(3, BQCharacter.class),
    GOBLIN(4, BQCharacter.class),
    OGRE(5, BQCharacter.class),
    SPECTRE(6, BQCharacter.class),
    CRAB(7, BQCharacter.class),
    BAT(8, BQCharacter.class),
    WIZARD(9, BQCharacter.class),
    EYE(10, BQCharacter.class),
    SNAKE(11, BQCharacter.class),
    SKELETON2(12, BQCharacter.class),
    BOSS(13, BQCharacter.class),
    DEATHKNIGHT(14, BQCharacter.class),

    // Armors
    FIREFOX(20, BQObject.class),
    CLOTHARMOR(21, BQObject.class),
    LEATHERARMOR(22, BQObject.class),
    MAILARMOR(23, BQObject.class),
    PLATEARMOR(24, BQObject.class),
    REDARMOR(25, BQObject.class),
    GOLDENARMOR(26, BQObject.class),

    // Objects
    FLASK(35, BQObject.class),
    BURGER(36, BQObject.class),
    CHEST(37, BQObject.class),
    FIREPOTION(38, BQObject.class),
    CAKE(39, BQObject.class),

    // NPCs
    GUARD(40, BQObject.class),
    KING(41, BQObject.class),
    OCTOCAT(42, BQObject.class),
    VILLAGEGIRL(43, BQObject.class),
    VILLAGER(44, BQObject.class),
    PRIEST(45, BQObject.class),
    SCIENTIST(46, BQObject.class),
    AGENT(47, BQObject.class),
    RICK(48, BQObject.class),
    NYAN(49, BQObject.class),
    SORCERER(50, BQObject.class),
    BEACHNPC(51, BQObject.class),
    FORESTNPC(52, BQObject.class),
    DESERTNPC(53, BQObject.class),
    LAVANPC(54, BQObject.class),
    CODER(55, BQObject.class),

    // Weapons
    SWORD1(60, BQObject.class),
    SWORD2(61, BQObject.class),
    REDSWORD(62, BQObject.class),
    GOLDENSWORD(63, BQObject.class),
    MORNINGSTAR(64, BQObject.class),
    AXE(65, BQObject.class),
    BLUESWORD(66, BQObject.class);

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
