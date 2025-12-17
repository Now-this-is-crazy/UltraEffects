package powercyphe.ultraeffects.mixin.hotbar_hud.transparency;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.gui.render.state.BlitRenderState;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.ultraeffects.util.AlphaOverrideAddon;

@Mixin(BlitRenderState.class)
public abstract class TexturedQuadGuiElementRenderStateMixin implements AlphaOverrideAddon {

    @Unique
    private Float alphaOverride = null;

    @ModifyReturnValue(method = "color", at = @At("RETURN"))
    private int ultraeffects$alphaOverride(int original) {
        if (this.ultraeffects$getAlphaOverride() != null) {
            return ARGB.color((int) (this.ultraeffects$getAlphaOverride() * 255), original);
        }
        return original;
    }

    @Override
    public void ultraeffects$setAlphaOverride(Float alpha) {
        this.alphaOverride = alpha;
    }

    public Float ultraeffects$getAlphaOverride() {
        return this.alphaOverride;
    }
}
