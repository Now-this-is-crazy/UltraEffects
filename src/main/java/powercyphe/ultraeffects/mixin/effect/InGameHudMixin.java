package powercyphe.ultraeffects.mixin.effect;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.ultraeffects.effect.EffectRegistry;
import powercyphe.ultraeffects.effect.OverlayEffect;

import java.util.List;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Inject(method = "renderMiscOverlays", at = @At("HEAD"))
    private void ultraeffects$effect(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        InGameHud hud = (InGameHud) (Object) this;

        List<OverlayEffect> effects = EffectRegistry.getEffectsByType(OverlayEffect.class);
        for (OverlayEffect overlayEffect : effects)
            overlayEffect.render(hud, context, tickCounter);
    }
}
