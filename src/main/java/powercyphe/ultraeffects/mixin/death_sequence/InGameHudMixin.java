package powercyphe.ultraeffects.mixin.death_sequence;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

@Mixin(Gui.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void ultraeffects$death_sequence(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        if (UltraEffectsUtil.isRunningDeathScreenOverhaul()) {
            ci.cancel();
        }
    }
}
