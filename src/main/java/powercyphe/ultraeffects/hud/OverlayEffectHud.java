package powercyphe.ultraeffects.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.fabricmc.fabric.api.client.rendering.v1.LayeredDrawerWrapper;
import powercyphe.ultraeffects.UltraEffectsClient;
import powercyphe.ultraeffects.registry.EffectRegistry;
import powercyphe.ultraeffects.effect.OverlayEffect;

import java.util.List;

public class OverlayEffectHud implements HudLayerRegistrationCallback {
    @Override
    public void register(LayeredDrawerWrapper layeredDrawerWrapper) {
        layeredDrawerWrapper.attachLayerAfter(IdentifiedLayer.MISC_OVERLAYS, UltraEffectsClient.id("overlay_effect"), ((context, tickCounter) -> {
            List<OverlayEffect> effects = EffectRegistry.getEffectsByType(OverlayEffect.class);
            for (OverlayEffect overlayEffect : effects) {
                overlayEffect.render(context, tickCounter);
            }
        }));
    }
}
