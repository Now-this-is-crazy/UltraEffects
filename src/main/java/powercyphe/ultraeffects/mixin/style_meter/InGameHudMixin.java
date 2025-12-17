package powercyphe.ultraeffects.mixin.style_meter;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.registry.EffectRegistry;

@Mixin(Gui.class)
public class InGameHudMixin {

    @Inject(method = "renderScoreboardSidebar", at = @At("HEAD"), cancellable = true)
    private void ultraeffects$renderScoreboard(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        if (ModConfig.styleMeterHideScoreboard && EffectRegistry.STYLE_METER_EFFECT.shouldDisplay()) {
            ci.cancel();
        }
    }
}
