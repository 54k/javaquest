package org.mozilla.browserquest.model.actor;

public enum BQType {

    WARRIOR(1),

    // Mobs
    RAT(2),
    SKELETON(3),
    GOBLIN(4),
    OGRE(5),
    SPECTRE(6),
    CRAB(7),
    BAT(8),
    WIZARD(9),
    EYE(10),
    SNAKE(11),
    SKELETON2(12),
    BOSS(13),
    DEATHKNIGHT(14),

    // Armors
    FIREFOX(20),
    CLOTHARMOR(21),
    LEATHERARMOR(22),
    MAILARMOR(23),
    PLATEARMOR(24),
    REDARMOR(25),
    GOLDENARMOR(26),

    // Objects
    FLASK(35),
    BURGER(36),
    CHEST(37),
    FIREPOTION(38),
    CAKE(39),

    // NPCs
    GUARD(40),
    KING(41),
    OCTOCAT(42),
    VILLAGEGIRL(43),
    VILLAGER(44),
    PRIEST(45),
    SCIENTIST(46),
    AGENT(47),
    RICK(48),
    NYAN(49),
    SORCERER(50),
    BEACHNPC(51),
    FORESTNPC(52),
    DESERTNPC(53),
    LAVANPC(54),
    CODER(55),

    // Weapons
    SWORD1(60),
    SWORD2(61),
    REDSWORD(62),
    GOLDENSWORD(63),
    MORNINGSTAR(64),
    AXE(65),
    BLUESWORD(66);

    private final int id;

    BQType(int id) {
        this.id = id;
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
}
