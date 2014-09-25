package org.mozilla.browserquest.util;

import java.util.Random;

public final class RandomUtils {

    private RandomUtils() {
    }

    public static int getRandomBetween(int min, int max) {
        Random random = new Random();
        return min + random.nextInt((max - min) + 1);
    }
}
