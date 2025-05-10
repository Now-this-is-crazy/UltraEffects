package powercyphe.ultraeffects.mixin.style_meter;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.registry.EffectRegistry;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V", at = @At("HEAD"), cancellable = true)
    private void ultraeffects$renderScoreboard(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (ModConfig.styleMeterHideScoreboard && EffectRegistry.STYLE_METER_EFFECT.shouldDisplay()) {
            ci.cancel();
        }
    }
}
