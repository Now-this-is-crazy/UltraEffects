package powercyphe.ultraeffects.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.EntityTrackingSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.random.RandomSeed;
import powercyphe.ultraeffects.UltraEffectsClient;
import powercyphe.ultraeffects.effect.EffectRegistry;
import powercyphe.ultraeffects.effect.OverlayEffect;
import powercyphe.ultraeffects.mixin.effect.InGameHudAccessor;

import java.util.List;

public class UltraEffectsUtil {

    public static List<Identifier> stringToIdentifierList(List<String> list) {
        DefaultedList<Identifier> available = DefaultedList.of();
        for (String str : list) {
            available.add(UltraEffectsClient.id(OverlayEffect.OVERLAY_PATH + str + ".png"));
        }
        return available;
    }

    public static void renderOverlay(InGameHud hud, DrawContext ctx, Identifier texture, float opacity) {
        ((InGameHudAccessor) hud).ultraeffects$renderOverlay(ctx, texture, opacity);
    }

    public static void parryEffect() {
        EffectRegistry.FREEZE_EFFECT.display();
        EffectRegistry.FLASH_EFFECT.display();
    }

    public static boolean isClientPlayer(Entity entity) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && client.player == entity) {
            return true;
        }
        return false;
    }

    public static ClientPlayerEntity getClientPlayer() {
        return MinecraftClient.getInstance().player;
    }

    public static void playSound(SoundEvent soundEvent, SoundCategory soundCategory, float volume, float pitch) {
        MinecraftClient.getInstance().getSoundManager().play(
                new EntityTrackingSoundInstance(soundEvent, soundCategory, volume, pitch, getClientPlayer(), RandomSeed.getSeed())
        );
    }
}
