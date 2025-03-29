package powercyphe.ultraeffects.mixin.effect;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.ultraeffects.effect.FlashEffect;
import powercyphe.ultraeffects.effect.GabrielEffect;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Inject(method = "renderMiscOverlays", at = @At("HEAD"))
    private void ultraeffects$effect(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (FlashEffect.flashTicks > 0) {
            this.renderOverlay(context, FlashEffect.FLASH_EFFECT_OVERLAY, FlashEffect.getOpacity());
        }
        if (GabrielEffect.fadeTicks > 0) {
            this.renderOverlay(context, GabrielEffect.overlay, GabrielEffect.getOpacity());
        }
    }
}
