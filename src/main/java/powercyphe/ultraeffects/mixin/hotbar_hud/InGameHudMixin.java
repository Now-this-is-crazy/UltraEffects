package powercyphe.ultraeffects.mixin.hotbar_hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.ultraeffects.ModConfig;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    private void ultraeffects$hotbar_hudHotbar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (ModConfig.hotbarHudEnabled) {
            ci.cancel();
        }
    }

    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    private void ultraeffects$hotbar_hudStatusBars(DrawContext context, CallbackInfo ci) {
        if (ModConfig.hotbarHudEnabled) {
            ci.cancel();
        }
    }

    @Inject(method = "renderHeldItemTooltip", at = @At("HEAD"), cancellable = true)
    private void ultraeffects$hotbar_hudTooltip(DrawContext context, CallbackInfo ci) {
        if (ModConfig.hotbarHudEnabled) {
            ci.cancel();
        }
    }
}
