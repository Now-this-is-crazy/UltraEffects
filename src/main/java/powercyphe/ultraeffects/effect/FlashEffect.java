package powercyphe.ultraeffects.effect;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.registry.EffectRegistry;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

import java.util.List;

public class FlashEffect extends OverlayEffect {
    public int flashTicks, lastFlashTicks = 0;

    @Override
    public void display() {
        setRandomOverlay();

        flashTicks = ModConfig.parryFlashTicks;
        lastFlashTicks = flashTicks;
    }

    @Override
    public void tick() {
        if (!EffectRegistry.FREEZE_EFFECT.shouldPause()) {
            if (flashTicks > 0) {
                flashTicks--;
            }
        }
    }

    @Override
    public void render(GuiGraphics ctx, DeltaTracker tickCounter) {
        if (this.flashTicks > 0) {
            UltraEffectsUtil.renderOverlay(ctx, this.getOverlay(), this.getOpacity());
        }
    }

    @Override
    public List<Identifier> getAllOverlays() {
        return UltraEffectsUtil.stringToIdentifierList(ModConfig.parryImages);
    }

    @Override
    public float getOpacity() {
        return Math.min(((float) flashTicks / (float) lastFlashTicks) * 0.5F, 0.5F);
    }
}
