package powercyphe.ultraeffects.mixin.effect;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.tracy.TracyFrameCapturer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.ultraeffects.registry.EffectRegistry;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;swapBuffers(Lnet/minecraft/client/util/tracy/TracyFrameCapturer;)V"))
    private boolean ultraeffects$freeze(Window instance, TracyFrameCapturer capturer) {
        return !EffectRegistry.FREEZE_EFFECT.shouldPause();
    }
}
