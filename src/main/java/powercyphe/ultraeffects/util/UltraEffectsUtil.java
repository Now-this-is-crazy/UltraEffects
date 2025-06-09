package powercyphe.ultraeffects.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.sound.EntityTrackingSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.RandomSeed;
import powercyphe.ultraeffects.UltraEffectsClient;
import powercyphe.ultraeffects.registry.EffectRegistry;
import powercyphe.ultraeffects.effect.OverlayEffect;

import java.util.List;

public class UltraEffectsUtil {

    public static void addStyle(String id, float points) {
        EffectRegistry.STYLE_METER_EFFECT.addStyle(Text.translatable("ultraeffects.style_meter.style." + id), points);
    }

    public static void addStyle(Text text, float points) {
        EffectRegistry.STYLE_METER_EFFECT.addStyle(text, points);
    }

    public static List<Identifier> stringToIdentifierList(List<String> list) {
        DefaultedList<Identifier> available = DefaultedList.of();
        for (String str : list) {
            available.add(UltraEffectsClient.id(OverlayEffect.OVERLAY_PATH + str + ".png"));
        }
        return available;
    }

    public static void renderOverlay(DrawContext ctx, Identifier texture, float opacity) {
        int i = ColorHelper.getWhite(opacity);
        ctx.drawTexture(RenderLayer::getGuiTexturedOverlay, texture, 0, 0, 0.0F, 0.0F, ctx.getScaledWindowWidth(), ctx.getScaledWindowHeight(), ctx.getScaledWindowWidth(), ctx.getScaledWindowHeight(), i);
    }

    public static void parryEffect(SoundEvent sound, boolean configCondition) {
        parryEffect(sound, configCondition, false);
    }

    public static void parryEffect(SoundEvent sound, boolean configCondtion, boolean disableForTick) {
        if (!configCondtion || UltraEffectsClient.PARRY_DISABLED) {
            return;
        }
        if (disableForTick) {
            UltraEffectsClient.PARRY_DISABLED = true;
        }
        UltraEffectsUtil.playSound(sound, SoundCategory.PLAYERS, 1F, 0.9F + Random.create().nextInt(4) * 0.05F);
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

    public static MinecraftClient getClient() {
        return MinecraftClient.getInstance();
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
