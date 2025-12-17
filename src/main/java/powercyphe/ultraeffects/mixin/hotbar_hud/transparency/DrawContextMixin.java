package powercyphe.ultraeffects.mixin.hotbar_hud.transparency;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.item.TrackingItemStackRenderState;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import powercyphe.ultraeffects.util.AlphaOverrideAddon;

@Mixin(GuiGraphics.class)
public class DrawContextMixin implements AlphaOverrideAddon {

    @Unique
    public Float alphaOverride = null;

    // Item
    @ModifyArg(method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/state/GuiItemRenderState;<init>(Ljava/lang/String;Lorg/joml/Matrix3x2f;Lnet/minecraft/client/renderer/item/TrackingItemStackRenderState;IILnet/minecraft/client/gui/navigation/ScreenRectangle;)V"),
    index = 2)
    private TrackingItemStackRenderState ultraeffects$alphaOverrideItem(TrackingItemStackRenderState state) {
        if (this.alphaOverride != null) {
            ((AlphaOverrideAddon) state).ultraeffects$setAlphaOverride(this.alphaOverride);
        }
        return state;
    }

    // Text
    @ModifyVariable(method = "drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)V", at = @At("HEAD"), index = 5, argsOnly = true)
    private int ultraeffects$alphaOverrideText(int color) {
        if (this.alphaOverride != null) {
            color = ARGB.color((int) (ARGB.alpha(color) * this.alphaOverride), color);
        }
        return color;
    }


    // Item Bar
    @ModifyArg(method = "renderItemBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(Lcom/mojang/blaze3d/pipeline/RenderPipeline;IIIII)V"), index = 5)
    private int ultraeffects$alphaOverrideItemBar(int color) {
        if (this.alphaOverride != null) {
            color = ARGB.color(ARGB.alpha(color) * this.alphaOverride, color);
        }
        return color;
    }

    // Implementation Alpha Override

    @Override
    public void ultraeffects$setAlphaOverride(Float alpha) {
        this.alphaOverride = alpha;
    }

    @Override
    public Float ultraeffects$getAlphaOverride() {
        return this.alphaOverride;
    }
}
