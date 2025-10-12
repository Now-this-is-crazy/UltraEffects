package powercyphe.ultraeffects.mixin.hotbar_hud;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.bar.Bar;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.UltraEffectsClient;
import powercyphe.ultraeffects.hud.HotbarHud;

@Mixin(Bar.class)
public interface BarMixin {

    @ModifyArgs(method = "drawExperienceLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)V"))
    private static void ultraeffects$hotbar_hudX(Args args, DrawContext context, TextRenderer textRenderer, int level) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (ModConfig.hotbarHudEnabled) {
            Text text = Text.translatable("gui.experience.level", new Object[]{level});
            args.set(2, (int) args.get(2) - ((context.getScaledWindowWidth() - textRenderer.getWidth(text)) / 2)
                    + UltraEffectsClient.HOTBAR_HUD.getX(client) + 60);
        }
    }

    @ModifyExpressionValue(method = "drawExperienceLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;getScaledWindowHeight()I"))
    private static int ultraeffects$hotbar_hudY(int original) {
        if (ModConfig.hotbarHudEnabled) {
            return original - 71;
        }
        return original;
    }

    @ModifyArg(method = "drawExperienceLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)V"), index = 4)
    private static int ultraeffects$hotbar_hud(int color) {
        if (ModConfig.hotbarHudEnabled && HotbarHud.getRenderState().getVisibility() != 1) {
            return ColorHelper.withAlpha(HotbarHud.getRenderState().getVisibility() * 255, color);
        }
        return color;
    }
}
