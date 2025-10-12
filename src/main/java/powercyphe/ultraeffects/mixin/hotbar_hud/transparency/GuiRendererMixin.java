package powercyphe.ultraeffects.mixin.hotbar_hud.transparency;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.state.ItemGuiElementRenderState;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import powercyphe.ultraeffects.util.AlphaOverrideAddon;

@Mixin(GuiRenderer.class)
public class GuiRendererMixin {

    @ModifyArgs(method = "prepareItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/state/TexturedQuadGuiElementRenderState;<init>(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/client/texture/TextureSetup;Lorg/joml/Matrix3x2f;IIIIFFFFILnet/minecraft/client/gui/ScreenRect;Lnet/minecraft/client/gui/ScreenRect;)V"))
    private void ultraeffects$alphaOverridePipeline(Args args, ItemGuiElementRenderState state, float u, float v, int pixelsPerItem, int itemAtlasSideLength) {
        AlphaOverrideAddon alphaOverride = (AlphaOverrideAddon) (state.state());

        if (alphaOverride.ultraeffects$getAlphaOverride() != null) {
            args.set(0, RenderPipelines.GUI_TEXTURED);
        }
    }

    @ModifyArgs(method = "prepareItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/state/TexturedQuadGuiElementRenderState;<init>(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/client/texture/TextureSetup;Lorg/joml/Matrix3x2f;IIIIFFFFILnet/minecraft/client/gui/ScreenRect;Lnet/minecraft/client/gui/ScreenRect;)V"))
    private void ultraeffects$alphaOverride(Args args, ItemGuiElementRenderState state, float u, float v, int pixelsPerItem, int itemAtlasSideLength) {
        AlphaOverrideAddon alphaOverride = (AlphaOverrideAddon) (state.state());

        if (alphaOverride.ultraeffects$getAlphaOverride() != null) {
            int color = args.get(11);
            args.set(11, ColorHelper.withAlpha((int) (ColorHelper.getAlpha(color) * alphaOverride.ultraeffects$getAlphaOverride()), color));
        }

    }
}
