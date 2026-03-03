package com.aparzero.maya.util;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

public class ComputationUtil {
    public static BigDecimal randomMultipleOf100UpTo2000() {
        int maxMultiplier = 2000 / 100;
        int randomMultiplier = ThreadLocalRandom.current().nextInt(1, maxMultiplier + 1);
        return BigDecimal.valueOf(randomMultiplier * 100L);
    }
}
