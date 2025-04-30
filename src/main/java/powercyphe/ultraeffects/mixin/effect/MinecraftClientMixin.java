package powercyphe.ultraeffects.mixin.effect;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.ultraeffects.effect.EffectRegistry;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;render(Lnet/minecraft/client/render/RenderTickCounter;Z)V"))
    private boolean ultraeffects$flash(GameRenderer instance, RenderTickCounter tickCounter, boolean tick) {
        return !EffectRegistry.FREEZE_EFFECT.shouldPause();
    }
}
