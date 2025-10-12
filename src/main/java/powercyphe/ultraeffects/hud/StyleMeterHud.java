package powercyphe.ultraeffects.hud;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;
import net.minecraft.util.math.ColorHelper;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.effect.StyleMeterEffect;
import powercyphe.ultraeffects.registry.EffectRegistry;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public class StyleMeterHud implements HudElement {

    @Override
    public void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;


        StyleMeterEffect effect = EffectRegistry.STYLE_METER_EFFECT;
        ModConfig.StyleMeterPosition position = ModConfig.styleMeterPosition;


        if (effect.shouldDisplay()) {
            int backgroundColor = ColorHelper.getArgb(
                    (int) (ModConfig.styleMeterBackgroundOpacity * 255),
                    ModConfig.styleMeterBackgroundColorRed,
                    ModConfig.styleMeterBackgroundColorGreen,
                    ModConfig.styleMeterBackgroundColorBlue
            );

            int x = position.x(context);
            int y = position.y(context);

            StyleMeterEffect.StyleRank styleRank = effect.styleRank;
            int styleRankColor = styleRank.getColor();

            MutableText prefix = Text.translatable(styleRank.getTranslationKey(false));
            MutableText suffix = Text.translatable(styleRank.getTranslationKey(true));

            UltraEffectsUtil.renderRoundBox(context, x, y, x+128, y+104, backgroundColor);

            context.getMatrices().pushMatrix();
            context.getMatrices().translate(x + 3, y + 3);
            context.getMatrices().scale(2F, 2F);
            context.drawText(textRenderer, prefix.formatted(Formatting.BOLD).withColor(styleRankColor), 0, 0, Colors.WHITE, true);


            context.getMatrices().translate(prefix.getString().length() * 7, 2);
            context.getMatrices().scale(0.8F, 0.8F);
            context.drawText(textRenderer, suffix.formatted(Formatting.WHITE), 0, 0, ColorHelper.withAlpha(255, 0xFFFFFF), true);
            context.getMatrices().popMatrix();

            drawLine(context, x + 2, y + 21, styleRankColor, EffectRegistry.STYLE_METER_EFFECT.getLineMultiplier());

            int styleX = x + 2;
            int styleY = y + 15;

            for (Pair<Text, Integer> pair : effect.styleList) {
                styleY += 11;
                context.drawWrappedTextWithShadow(textRenderer, Text.literal("+ ").append(pair.getLeft().copy().formatted(Formatting.WHITE)), styleX, styleY, 120, Colors.LIGHT_GRAY);
            }
        }
    }

    public static void drawLine(DrawContext context, int x, int y, int color, float lineMultiplier) {
        context.fill(x, y, x + 124, y + 2, ColorHelper.fullAlpha(0x666666));
        context.fill(x, y, (int) (x + (124 * lineMultiplier)), y + 2, ColorHelper.fullAlpha(color));
    }
}
