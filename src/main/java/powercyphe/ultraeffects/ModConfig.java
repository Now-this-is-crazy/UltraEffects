package powercyphe.ultraeffects;

import eu.midnightdust.lib.config.MidnightConfig;

public class ModConfig extends MidnightConfig {

    @Entry(min = 0, max = 20)
    public static int flashTicks = 4;

    @Entry(min = 0, max = 10)
    public static int freezeTicks = 2;

    @Entry(min = 0, max = 100)
    public static int gabrielPercentage = 0;
}
