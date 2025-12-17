package powercyphe.ultraeffects.mixin.hotbar_hud.transparency;

import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.state.GuiItemRenderState;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import powercyphe.ultraeffects.util.AlphaOverrideAddon;

@Mixin(GuiRenderer.class)
public class GuiRendererMixin {

    @ModifyArgs(method = "submitBlitFromItemAtlas", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/state/BlitRenderState;<init>(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/client/gui/render/TextureSetup;Lorg/joml/Matrix3x2f;IIIIFFFFILnet/minecraft/client/gui/navigation/ScreenRectangle;Lnet/minecraft/client/gui/navigation/ScreenRectangle;)V"))
    private void ultraeffects$alphaOverride(Args args, GuiItemRenderState state, float u, float v, int pixelsPerItem, int itemAtlasSideLength) {
        AlphaOverrideAddon alphaOverride = (AlphaOverrideAddon) (state.itemStackRenderState());

        if (alphaOverride.ultraeffects$getAlphaOverride() != null) {
            args.set(0, RenderPipelines.GUI_TEXTURED);

            int color = args.get(11);
            args.set(11, ARGB.color((int) (ARGB.alpha(color) * alphaOverride.ultraeffects$getAlphaOverride()), color));
        }
    }
}
