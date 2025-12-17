package powercyphe.ultraeffects.hud;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import powercyphe.ultraeffects.effect.OverlayEffect;
import powercyphe.ultraeffects.registry.EffectRegistry;

import java.util.List;

public class OverlayEffectHud implements HudElement {

    @Override
    public void render(GuiGraphics context, DeltaTracker tickCounter) {
        List<OverlayEffect> effects = EffectRegistry.getEffectsByType(OverlayEffect.class);
        for (OverlayEffect overlayEffect : effects) {
            overlayEffect.render(context, tickCounter);
        }
    }
}
