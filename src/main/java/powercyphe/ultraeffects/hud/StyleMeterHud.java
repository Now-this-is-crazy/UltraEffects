package powercyphe.ultraeffects.hud;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.ARGB;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Tuple;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.effect.StyleMeterEffect;
import powercyphe.ultraeffects.registry.EffectRegistry;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public class StyleMeterHud implements HudElement {

    @Override
    public void render(GuiGraphics context, DeltaTracker tickCounter) {
        Minecraft client = Minecraft.getInstance();
        Font textRenderer = client.font;


        StyleMeterEffect effect = EffectRegistry.STYLE_METER_EFFECT;
        ModConfig.StyleMeterPosition position = ModConfig.styleMeterPosition;


        if (effect.shouldDisplay()) {
            int backgroundColor = ARGB.color(
                    (int) (ModConfig.styleMeterBackgroundOpacity * 255),
                    ModConfig.styleMeterBackgroundColorRed,
                    ModConfig.styleMeterBackgroundColorGreen,
                    ModConfig.styleMeterBackgroundColorBlue
            );

            int x = position.x(context);
            int y = position.y(context);

            StyleMeterEffect.StyleRank styleRank = effect.styleRank;
            int styleRankColor = styleRank.getColor();

            MutableComponent prefix = Component.translatable(styleRank.getTranslationKey(false));
            MutableComponent suffix = Component.translatable(styleRank.getTranslationKey(true));

            UltraEffectsUtil.renderRoundBox(context, x, y, x+128, y+104, backgroundColor);

            context.pose().pushMatrix();
            context.pose().translate(x + 3, y + 3);
            context.pose().scale(2F, 2F);
            context.drawString(textRenderer, prefix.withStyle(ChatFormatting.BOLD).withColor(styleRankColor), 0, 0, CommonColors.WHITE, true);


            context.pose().translate(prefix.getString().length() * 7, 2);
            context.pose().scale(0.8F, 0.8F);
            context.drawString(textRenderer, suffix.withStyle(ChatFormatting.WHITE), 0, 0, ARGB.color(255, 0xFFFFFF), true);
            context.pose().popMatrix();

            drawLine(context, x + 2, y + 21, styleRankColor, EffectRegistry.STYLE_METER_EFFECT.getLineMultiplier());

            int styleX = x + 2;
            int styleY = y + 15;

            for (Tuple<Component, Integer> pair : effect.styleList) {
                styleY += 11;
                context.drawWordWrap(textRenderer, Component.literal("+ ").append(pair.getA().copy().withStyle(ChatFormatting.WHITE)), styleX, styleY, 120, CommonColors.LIGHT_GRAY);
            }
        }
    }

    public static void drawLine(GuiGraphics context, int x, int y, int color, float lineMultiplier) {
        context.fill(x, y, x + 124, y + 2, ARGB.opaque(0x666666));
        context.fill(x, y, (int) (x + (124 * lineMultiplier)), y + 2, ARGB.opaque(color));
    }
}
