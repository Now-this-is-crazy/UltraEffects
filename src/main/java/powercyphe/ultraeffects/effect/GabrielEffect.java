package powercyphe.ultraeffects.effect;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.UltraEffectsClient;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

import java.util.List;

public class GabrielEffect extends OverlayEffect {
    public String overlay = ModConfig.gabrielImages.getFirst();
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
        ClientPlayerEntity player = UltraEffectsUtil.getClientPlayer();

        if (!EffectRegistry.FREEZE_EFFECT.shouldPause()) {
            if (fadeTicks > 0) {
                fadeTicks--;
            } else {
                if (waitTicks > 0) {
                    waitTicks--;
                } else if (player != null) {
                    float threshold = 0;
                    switch (ModConfig.gabrielThresholdMode) {
                        case HEALTH_PERCENTAGE -> threshold = (player.getMaxHealth() * ((float) ModConfig.gabrielThreshold / 100));
                        case REMAINING_HEALTH -> threshold = ModConfig.gabrielThreshold;
                    }

                    if (!player.isDead() && player.getHealth() <= threshold) {
                        UltraEffectsUtil.playSound(UltraEffectsClient.GABRIEL, SoundCategory.PLAYERS, 0.5F, 1F);
                        display();
                    }
                }
            }
        }
    }

    @Override
    public void render(InGameHud inGameHud, DrawContext ctx, RenderTickCounter tickCounter) {
        if (this.fadeTicks > 0) {
            UltraEffectsUtil.renderOverlay(inGameHud, ctx, this.getOverlay(), this.getOpacity());
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
