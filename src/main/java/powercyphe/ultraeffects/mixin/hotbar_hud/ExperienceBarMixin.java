package powercyphe.ultraeffects.mixin.hotbar_hud;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.contextualbar.ExperienceBarRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.ultraeffects.ModConfig;

@Mixin(ExperienceBarRenderer.class)
public class ExperienceBarMixin {

    @Inject(method = "renderBackground", at = @At("HEAD"), cancellable = true)
    private void ultraeffects$hotbar_hudX(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        if (ModConfig.hotbarHudEnabled) {
            ci.cancel();
        }
    }

}
