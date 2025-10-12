package powercyphe.ultraeffects.mixin.hotbar_hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.bar.ExperienceBar;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.ultraeffects.ModConfig;

@Mixin(ExperienceBar.class)
public class ExperienceBarMixin {

    @Inject(method = "renderBar", at = @At("HEAD"), cancellable = true)
    private void ultraeffects$hotbar_hudX(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (ModConfig.hotbarHudEnabled) {
            ci.cancel();
        }
    }

}
