package powercyphe.ultraeffects.mixin.hotbar_hud.transparency;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import powercyphe.ultraeffects.util.AlphaOverrideAddon;

@Mixin(DrawContext.class)
public class DrawContextMixin implements AlphaOverrideAddon {

    @Unique
    public Float alphaOverride = null;

    // Item
    @ModifyArg(method = "drawItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;III)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/item/ItemModelManager;clearAndUpdate(Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)V"),
    index = 0)
    private ItemRenderState ultraeffects$alphaOverrideItem(ItemRenderState original) {
        if (this.alphaOverride != null) {
            ((AlphaOverrideAddon) original).ultraeffects$setAlphaOverride(this.alphaOverride);
        }
        return original;
    }

    // Text
    @ModifyVariable(method = "drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;IIIZ)V", at = @At("HEAD"), index = 5, argsOnly = true)
    private int ultraeffects$alphaOverrideText(int color) {
        if (this.alphaOverride != null) {
            color = ColorHelper.withAlpha((int) (ColorHelper.getAlpha(color) * this.alphaOverride), color);
        }
        return color;
    }


    // Item Bar
    @ModifyArg(method = "drawItemBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(Lcom/mojang/blaze3d/pipeline/RenderPipeline;IIIII)V"), index = 5)
    private int ultraeffects$alphaOverrideItemBar(int color) {
        if (this.alphaOverride != null) {
            color = ColorHelper.withAlpha(ColorHelper.getAlpha(color) * this.alphaOverride, color);
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
