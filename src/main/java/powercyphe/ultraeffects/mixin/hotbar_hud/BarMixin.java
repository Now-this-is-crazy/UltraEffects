package powercyphe.ultraeffects.mixin.hotbar_hud;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.UltraEffectsClient;
import powercyphe.ultraeffects.hud.HotbarHud;

@Mixin(ContextualBarRenderer.class)
public interface BarMixin {

    @ModifyArgs(method = "renderExperienceLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)V"))
    private static void ultraeffects$hotbar_hudX(Args args, GuiGraphics context, Font textRenderer, int level) {
        Minecraft client = Minecraft.getInstance();

        if (ModConfig.hotbarHudEnabled) {
            Component text = Component.translatable("gui.experience.level", new Object[]{level});
            args.set(2, (int) args.get(2) - ((context.guiWidth() - textRenderer.width(text)) / 2)
                    + UltraEffectsClient.HOTBAR_HUD.getX(client) + 60);
        }
    }

    @ModifyExpressionValue(method = "renderExperienceLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;guiHeight()I"))
    private static int ultraeffects$hotbar_hudY(int original) {
        if (ModConfig.hotbarHudEnabled) {
            return original - 71;
        }
        return original;
    }

    @ModifyArg(method = "renderExperienceLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)V"), index = 4)
    private static int ultraeffects$hotbar_hud(int color) {
        if (ModConfig.hotbarHudEnabled && HotbarHud.getRenderState().getVisibility() != 1) {
            return ARGB.color(HotbarHud.getRenderState().getVisibility() * 255, color);
        }
        return color;
    }
}
