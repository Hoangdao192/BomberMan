package Utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomInt {
    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min,max);
    }
}
