package powercyphe.ultraeffects.effect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.UltraEffectsClient;

public class FlashEffect {
    public static Identifier FLASH_EFFECT_OVERLAY = UltraEffectsClient.id("textures/misc/flash.png");

    public static int flashTicks, lastFlashTicks, freezeTicks, lastFreezeTicks = 0;

    public static float getOpacity() {
        return Math.min(((float) flashTicks / (float) lastFlashTicks) * 0.5F, 0.5F);
    }

    public static boolean shouldPause() {
        return freezeTicks > 0 && freezeTicks < lastFreezeTicks && !MinecraftClient.getInstance().isPaused();
    }

    public static void tick() {
        if (freezeTicks > 0) {
            freezeTicks--;
        } else {
            if (flashTicks > 0) {
                flashTicks--;
            }
        }
    }

    public static void display() {
        flashTicks = ModConfig.flashTicks;
        lastFlashTicks = flashTicks;

        freezeTicks = ModConfig.freezeTicks+1;
        lastFreezeTicks = freezeTicks;
    }
}
