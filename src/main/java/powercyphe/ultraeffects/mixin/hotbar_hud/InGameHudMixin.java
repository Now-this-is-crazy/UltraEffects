package powercyphe.ultraeffects.mixin.hotbar_hud;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.ultraeffects.ModConfig;

@Mixin(Gui.class)
public abstract class InGameHudMixin {

    @Inject(method = "renderItemHotbar", at = @At("HEAD"), cancellable = true)
    private void ultraeffects$hotbar_hudHotbar(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        if (ModConfig.hotbarHudEnabled) {
            ci.cancel();
        }
    }

    @Inject(method = "renderPlayerHealth", at = @At("HEAD"), cancellable = true)
    private void ultraeffects$hotbar_hudStatusBars(GuiGraphics context, CallbackInfo ci) {
        if (ModConfig.hotbarHudEnabled) {
            ci.cancel();
        }
    }

    @Inject(method = "renderSelectedItemName", at = @At("HEAD"), cancellable = true)
    private void ultraeffects$hotbar_hudTooltip(GuiGraphics context, CallbackInfo ci) {
        if (ModConfig.hotbarHudEnabled) {
            ci.cancel();
        }
    }
}
