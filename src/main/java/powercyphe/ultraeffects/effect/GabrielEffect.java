package powercyphe.ultraeffects.effect;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.registry.EffectRegistry;
import powercyphe.ultraeffects.registry.ModSounds;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

import java.util.List;

public class GabrielEffect extends OverlayEffect {
    public int fadeTicks, lastFadeTicks, waitTicks = 0;

    @Override
    public void display() {
        setRandomOverlay();

        waitTicks = ModConfig.gabrielWaitTicks;
        fadeTicks = ModConfig.gabrielFlashTicks;
        lastFadeTicks = fadeTicks;
    }

    @Override
    public void tick() {
        LocalPlayer player = UltraEffectsUtil.getLocalPlayer();

        if (!EffectRegistry.FREEZE_EFFECT.shouldPause()) {
            if (fadeTicks > 0) {
                fadeTicks--;
            } else {
                if (waitTicks > 0) {
                    waitTicks--;
                } else if (player != null) {
                    float threshold;
                    switch (ModConfig.gabrielThresholdMode) {
                        case HEALTH_PERCENTAGE -> threshold = (player.getMaxHealth() * ((float) ModConfig.gabrielThreshold / 100));
                        case null, default -> threshold = ModConfig.gabrielThreshold;
                    }

                    if (!player.isDeadOrDying() && player.getHealth() <= threshold) {
                        UltraEffectsUtil.playSound(ModSounds.GABRIEL, SoundSource.PLAYERS, 1F, 0.5F);
                        display();
                    }
                }
            }
        }
    }

    @Override
    public void render(GuiGraphics ctx, DeltaTracker tickCounter) {
        if (this.fadeTicks > 0) {
            UltraEffectsUtil.renderOverlay(ctx, this.getOverlay(), this.getOpacity());
        }
    }

    @Override
    public List<Identifier> getAllOverlays() {
        return UltraEffectsUtil.stringToIdentifierList(ModConfig.gabrielImages);
    }

    @Override
    public float getOpacity() {
        return Math.min(((float) fadeTicks / (float) lastFadeTicks) * 0.5F, 0.5F);
    }

}
