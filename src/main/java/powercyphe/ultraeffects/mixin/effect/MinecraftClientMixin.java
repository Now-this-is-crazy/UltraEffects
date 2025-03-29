package powercyphe.ultraeffects.mixin.effect;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.ultraeffects.effect.FlashEffect;
import powercyphe.ultraeffects.effect.GabrielEffect;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow private volatile boolean paused;

    @Inject(method = "tick", at = @At("HEAD"))
    private void ultraeffects$effect(CallbackInfo ci) {
        if (!this.paused) {
            FlashEffect.tick();
            GabrielEffect.tick();
        }
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;render(Lnet/minecraft/client/render/RenderTickCounter;Z)V"))
    private boolean ultraeffects$flash(GameRenderer instance, RenderTickCounter tickCounter, boolean tick) {
        return !FlashEffect.shouldPause();
    }
}
