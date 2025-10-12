package powercyphe.ultraeffects;

import com.mojang.datafixers.util.Function3;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.gui.DrawContext;
import powercyphe.ultraeffects.hud.state.HotbarHudBarRenderState;

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


    // Hotbar Hud
    @Entry(category = "hotbarHud")
    public static boolean hotbarHudEnabled = true;
    @Entry(category = "hotbarHud")
    public static boolean hotbarHudCursor = true;
    @Entry(category = "hotbarHud")
    public static HotbarHudSide hotbarHudSide = HotbarHudSide.MAIN_ARM;
    @Entry(category = "hotbarHud")
    public static HotbarHudChatFocusModification hotbarHudChatFocusModification = HotbarHudChatFocusModification.TRANSPARENT_HUD;
    @Entry(category = "hotbarHud")
    public static HotbarHudItemNameDisplay hotbarHudItemNameDisplay = HotbarHudItemNameDisplay.VANILLA;
    @Entry(category = "hotbarHud")
    public static HotbarHudHealthNumberDisplay hotbarHudHealthNumberDisplay = HotbarHudHealthNumberDisplay.ULTRAKILL;
    @Entry(category = "hotbarHud")
    public static HotbarHudColors hotbarHudColors = HotbarHudColors.ULTRAKILL;


    // Background Modification
    @Comment(category = "hotbarHud")
    public static Comment hotbarHudBackgroundspacer;
    @Comment(category = "hotbarHud")
    public static Comment hotbarHudBackground;
    @Entry(category = "hotbarHud", min = 0F, max = 1F, isSlider = true)
    public static float hotbarHudBackgroundOpacity = 0.7F;
    @Comment(category = "hotbarHud")
    public static Comment hotbarHudBackgroundColorspacer;
    @Comment(category = "hotbarHud")
    public static Comment hotbarHudBackgroundColor;
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudBackgroundColorRed = 20;
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudBackgroundColorGreen = 20;
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudBackgroundColorBlue = 20;


    // Health Color
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Comment(category = "hotbarHud")
    public static Comment hotbarHudHealthColorspacer;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Comment(category = "hotbarHud")
    public static Comment hotbarHudHealthColor;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudHealthColorRed = 0;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudHealthColorGreen = 0;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudHealthColorBlue = 0;

    // Absorption Color
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Comment(category = "hotbarHud")
    public static Comment hotbarHudAbsorptionColorspacer;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Comment(category = "hotbarHud")
    public static Comment hotbarHudAbsorptionColor;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudAbsorptionColorRed = 0;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudAbsorptionColorGreen = 0;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudAbsorptionColorBlue = 0;

    /*

    // Armor Color
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Comment(category = "hotbarHud")
    public static Comment hotbarHudArmorColorspacer;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Comment(category = "hotbarHud")
    public static Comment hotbarHudArmorColor;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudArmorColorRed = 0;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudArmorColorGreen = 0;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudArmorColorBlue = 0;

    // Air Color
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Comment(category = "hotbarHud")
    public static Comment hotbarHudAirColorspacer;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Comment(category = "hotbarHud")
    public static Comment hotbarHudAirColor;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudAirColorRed = 0;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudAirColorGreen = 0;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudAirColorBlue = 0;

    */

    // Hunger Color
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Comment(category = "hotbarHud")
    public static Comment hotbarHudHungerColorspacer;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Comment(category = "hotbarHud")
    public static Comment hotbarHudHungerColor;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudHungerColorRed = 0;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudHungerColorGreen = 0;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudHungerColorBlue = 0;

    // Experience Color
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Comment(category = "hotbarHud")
    public static Comment hotbarHudExperienceColorspacer;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Comment(category = "hotbarHud")
    public static Comment hotbarHudExperienceColor;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudExperienceColorRed = 0;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudExperienceColorGreen = 0;
    @Condition(requiredOption = "ultraeffects:hotbarHudColors", requiredValue = "CUSTOM")
    @Entry(category = "hotbarHud", min = 0, max = 255, isSlider = true)
    public static int hotbarHudExperienceColorBlue = 0;



    // Style Meter
    @Entry(category = "styleMeter")
    public static StyleMeterDisplayCondition styleMeterDisplayCondition = StyleMeterDisplayCondition.ANY_STYLE;
    @Entry(category = "styleMeter")
    public static StyleMeterPosition styleMeterPosition = StyleMeterPosition.TOP_RIGHT;

    // Background Modification
    @Comment(category = "styleMeter")
    public static Comment styleMeterBackgroundspacer;
    @Comment(category = "styleMeter")
    public static Comment styleMeterBackground;
    @Entry(category = "styleMeter", min = 0F, max = 1F, isSlider = true)
    public static float styleMeterBackgroundOpacity = 0.7F;
    @Comment(category = "styleMeter")
    public static Comment styleMeterBackgroundColorspacer;
    @Comment(category = "styleMeter")
    public static Comment styleMeterBackgroundColor;
    @Entry(category = "styleMeter", min = 0, max = 255, isSlider = true)
    public static int styleMeterBackgroundColorRed = 20;
    @Entry(category = "styleMeter", min = 0, max = 255, isSlider = true)
    public static int styleMeterBackgroundColorGreen = 20;
    @Entry(category = "styleMeter", min = 0, max = 255, isSlider = true)
    public static int styleMeterBackgroundColorBlue = 20;


    @Comment(category = "styleMeter")
    public static Comment styleMeterspacer;
    @Entry(category = "styleMeter")
    public static boolean styleMeterSound = true;
    @Entry(category = "styleMeter")
    public static boolean styleMeterHideScoreboard = true;


    // Death Screen
    @Entry(category = "misc")
    public static boolean deathScreenOverhaul = true;

    // Eternal Gabriel
    @Comment(category = "misc")
    public static Comment gabrielspacer;
    @Entry(category = "misc")
    public static GabrielMode gabrielThresholdMode = GabrielMode.HEALTH_PERCENTAGE;
    @Entry(category = "misc", min = 0, max = 100)
    public static int gabrielThreshold = 0;
    @Entry(category = "misc", min = 0, max = 100)
    public static int gabrielWaitTicks = 10;
    @Entry(category = "misc", min = 0, max = 100)
    public static int gabrielFlashTicks = 10;

    @Comment(category = "misc")
    public static Comment gabrielImagesspacer;

    @Entry(category = "misc")
    public static List<String> gabrielImages = Arrays.asList(
            "gabriel1",
            "gabriel2",
            "gabriel3",
            "gabriel4"
    );

    public enum HotbarHudSide {
        MAIN_ARM(),
        LEFT(),
        RIGHT()
    }

    public enum HotbarHudChatFocusModification {
        SHIFTED_CHAT(),
        TRANSPARENT_HUD(),
        HIDDEN_HUD()
    }

    public enum HotbarHudItemNameDisplay {
        ALWAYS(),
        VANILLA(),
        NEVER()
    }

    public enum HotbarHudHealthNumberDisplay {
        ULTRAKILL((healthBar, absorptionBar, tickProgress) -> {
            return Math.round(healthBar.getProgress(tickProgress) * 100) +
                    Math.round(absorptionBar.getProgress(tickProgress) * 100);
        }),
        VANILLA((healthBar, absorptionBar, tickProgress) -> {
            return Math.round(healthBar.getCurrent(tickProgress) + absorptionBar.getCurrent(tickProgress));
        }),
        NEVER((healthBar, absorptionBar, tickProgress) -> 0)
        ;

        public final Function3<HotbarHudBarRenderState, HotbarHudBarRenderState, Float, Integer> healthGetter;

        HotbarHudHealthNumberDisplay(Function3<HotbarHudBarRenderState, HotbarHudBarRenderState, Float, Integer> healthGetter) {
            this.healthGetter = healthGetter;
        }
    }

    public enum HotbarHudColors {
        ULTRAKILL(),
        VANILLA(),
        CUSTOM()
    }

    public enum StyleMeterDisplayCondition {
        ALWAYS(),
        ANY_STYLE(),
        POINTS_ONLY(),
        EVENTS_ONLY(),
        NEVER()
    }

    public enum StyleMeterPosition {
        TOP_LEFT(false, false),
        BOTTOM_LEFT(false, true),
        TOP_RIGHT(true, false),
        BOTTOM_RIGHT(true, true)
        ;

        private final boolean isRight;
        private final boolean isBottom;

        StyleMeterPosition(boolean isRight, boolean isBottom) {
            this.isRight = isRight;
            this.isBottom = isBottom;
        }

        public int x(DrawContext context) {
            return this.isRight ? context.getScaledWindowWidth() - 132 : 4;
        }

        public int y(DrawContext context) {
            return this.isBottom ? context.getScaledWindowHeight() - 108 : 4;
        }

    }

    public enum GabrielMode {
        REMAINING_HEALTH(),
        HEALTH_PERCENTAGE()
    }
}
