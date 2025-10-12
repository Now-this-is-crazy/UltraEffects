package powercyphe.ultraeffects.mixin.death_sequence;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void ultraeffects$death_sequence(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (UltraEffectsUtil.isRunningDeathScreenOverhaul()) {
            ci.cancel();
        }
    }
}
