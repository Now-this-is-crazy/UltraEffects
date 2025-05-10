package powercyphe.ultraeffects;

import eu.midnightdust.lib.config.MidnightConfig;

import java.util.Arrays;
import java.util.List;

public class ModConfig extends MidnightConfig {

    // Parries & Mace Hits
    @Entry(category = "parry", min = 0, max = 20)
    public static int parryFlashTicks = 6;
    @Entry(category = "parry", min = 0, max = 10)
    public static int parryFreezeTicks = 3;

    @Comment(category = "parry")
    public static Comment parryspacer1;

    @Entry(category = "parry")
    public static boolean parryProjectilesEnabled = true;
    @Entry(category = "parry")
    public static boolean parryMaceEnabled = true;
    @Entry(category = "parry")
    public static boolean parryShieldEnabled = true;
    @Entry(category = "parry")
    public static boolean parryDeathProtectorEnabled = true;

    @Comment(category = "parry")
    public static Comment parryspacer2;

    @Entry(category = "parry")
    public static List<String> parryImages = Arrays.asList(
            "flash"
    );

    // Style Meter
    @Entry(category = "styleMeter")
    public static StyleMeterMode styleMeterDisplayCondition = StyleMeterMode.ANY_STYLE;
    @Entry(category = "styleMeter")
    public static StyleMeterPosition styleMeterPosition = StyleMeterPosition.TOP_RIGHT;
    @Entry(category = "styleMeter")
    public static boolean styleMeterSound = true;

    @Entry(category = "styleMeter")
    public static boolean styleMeterHideScoreboard = true;


    // Eternal Gabriel
    @Entry(category = "misc")
    public static GabrielMode gabrielThresholdMode = GabrielMode.HEALTH_PERCENTAGE;
    @Entry(category = "misc", min = 0, max = 100)
    public static int gabrielThreshold = 0;
    @Entry(category = "misc", min = 0, max = 100)
    public static int gabrielWaitTicks = 10;
    @Entry(category = "misc", min = 0, max = 100)
    public static int gabrielFlashTicks = 10;

    @Comment(category = "misc")
    public static Comment gabrielspacer;

    @Entry(category = "misc")
    public static List<String> gabrielImages = Arrays.asList(
            "gabriel1",
            "gabriel2",
            "gabriel3",
            "gabriel4"
    );


    public enum StyleMeterMode {
        ALWAYS(),
        ANY_STYLE(),
        POINTS_ONLY(),
        EVENTS_ONLY(),
        NEVER()
    }

    public enum StyleMeterPosition {
        TOP_LEFT(false, false, 4, 4),
        BOTTOM_LEFT(false, true, 4, -108),
        TOP_RIGHT(true, false, -132, 4),
        BOTTOM_RIGHT(true, true, -132, -108)
        ;

        public final boolean isRight;
        public final boolean isBottom;

        public final int x;
        public final int y;

        StyleMeterPosition(boolean isRight, boolean isBottom, int x, int y) {
            this.isRight = isRight;
            this.isBottom = isBottom;
            this.x = x;
            this.y = y;
        }

    }

    public enum GabrielMode {
        REMAINING_HEALTH(),
        HEALTH_PERCENTAGE()
    }
}
