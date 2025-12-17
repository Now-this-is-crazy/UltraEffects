package powercyphe.ultraeffects.mixin.effect;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.blaze3d.TracyFrameCapture;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.ultraeffects.registry.EffectRegistry;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {

    @WrapWithCondition(method = "runTick", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;updateDisplay(Lcom/mojang/blaze3d/TracyFrameCapture;)V"))
    private boolean ultraeffects$freeze(Window instance, TracyFrameCapture capturer) {
        return !EffectRegistry.FREEZE_EFFECT.shouldPause();
    }
}
