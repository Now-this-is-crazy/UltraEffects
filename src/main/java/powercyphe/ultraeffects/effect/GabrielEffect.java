package powercyphe.ultraeffects.effect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.EntityTrackingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.random.Random;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.UltraEffectsClient;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

import java.util.Arrays;
import java.util.List;

public class GabrielEffect {
    public static List<Identifier> GABRIEL_EFFECT_OVERLAY = List.of(
            UltraEffectsClient.id("textures/misc/gabriel1.png"),
            UltraEffectsClient.id("textures/misc/gabriel2.png"),
            UltraEffectsClient.id("textures/misc/gabriel3.png"),
            UltraEffectsClient.id("textures/misc/gabriel4.png")
    );

    public static Identifier overlay = GABRIEL_EFFECT_OVERLAY.getFirst();
    public static int fadeTicks, lastFadeTicks, waitTicks = 0;

    public static float getOpacity() {
        return Math.min(((float) fadeTicks / (float) lastFadeTicks) * 0.5F, 0.5F);
    }

    public static void tick() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (fadeTicks > 0) {
            fadeTicks--;
        } else {
            if (waitTicks > 0) {
                waitTicks--;
            } else if (player != null) {
                if (!player.isDead() && player.getHealth() <= (player.getMaxHealth() * ((float) ModConfig.gabrielPercentage / 100)) ) {
                    UltraEffectsUtil.playSound(UltraEffectsClient.GABRIEL, SoundCategory.PLAYERS, 0.5F, 1F);
                    display();
                }
            }
        }
    }

    public static void display() {
        DefaultedList<Identifier> available = DefaultedList.of();
        available.addAll(GABRIEL_EFFECT_OVERLAY);
        available.remove(overlay);
        overlay = available.get(Random.create().nextBetween(0, available.size()-1));

        waitTicks = 10;
        fadeTicks = 10;
        lastFadeTicks = fadeTicks;
    }
}
