package org.mozilla.browserquest;

public enum MobTypes {
    // Mobs
    //    RAT: 2,
    //    SKELETON: 3,
    //    GOBLIN: 4,
    //    OGRE: 5,
    //    SPECTRE: 6,
    //    CRAB: 7,
    //    BAT: 8,
    //    WIZARD: 9,
    //    EYE: 10,
    //    SNAKE: 11,
    //    SKELETON2: 12,
    //    BOSS: 13,
    //    DEATHKNIGHT: 14,

    RAT(2), SKELETON(3), GOBLIN(4), OGRE(5), SPECTRE(6), CRAB(7), BAT(8), WIZARD(9), EYE(10), SNAKE(11), SKELETON2(12), BOSS(13), DEATHKNIGHT(14);

    private int kind;

    private MobTypes(int kind) {
        this.kind = kind;
    }

    public int getKind() {
        return kind;
    }

    public static int getKindFromString(String name) {
        return MobTypes.valueOf(name.toUpperCase()).getKind();
    }
}
