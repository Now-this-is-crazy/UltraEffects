package powercyphe.ultraeffects.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.EntityTrackingSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import powercyphe.ultraeffects.UltraEffectsClient;

public class UltraEffectsUtil {

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
                new EntityTrackingSoundInstance(soundEvent, soundCategory, volume, pitch, getClientPlayer(), 0)
        );
    }
}
