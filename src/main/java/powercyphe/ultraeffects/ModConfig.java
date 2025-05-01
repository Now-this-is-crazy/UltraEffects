package powercyphe.ultraeffects;

import eu.midnightdust.lib.config.MidnightConfig;

import java.util.Arrays;
import java.util.List;

public class ModConfig extends MidnightConfig {

    // Parries & Mace Hits
    @Comment(centered = true)
    public static Comment parryCategory;

    @Entry(min = 0, max = 20)
    public static int parryFlashTicks = 6;
    @Entry(min = 0, max = 10)
    public static int parryFreezeTicks = 3;

    @Entry
    public static List<String> parryImages = Arrays.asList(
            "flash"
    );

    // Eternal Gabriel
    @Comment
    public static Comment gabrielspacer;
    @Comment(centered = true)
    public static Comment gabrielCategory;

    @Entry
    public static List<String> gabrielImages = Arrays.asList(
            "gabriel1",
            "gabriel2",
            "gabriel3",
            "gabriel4"
    );
    @Entry
    public static GabrielMode gabrielThresholdMode = GabrielMode.HEALTH_PERCENTAGE;
    @Entry(min = 0, max = 100)
    public static int gabrielThreshold = 0;
    @Entry(min = 0, max = 100)
    public static int gabrielWaitTicks = 10;
    @Entry(min = 0, max = 100)
    public static int gabrielFlashTicks = 10;


    public enum GabrielMode {
        REMAINING_HEALTH(),
        HEALTH_PERCENTAGE();
    }
}
