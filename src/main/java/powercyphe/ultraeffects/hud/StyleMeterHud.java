package powercyphe.ultraeffects.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.fabricmc.fabric.api.client.rendering.v1.LayeredDrawerWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.ColorHelper;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.UltraEffectsClient;
import powercyphe.ultraeffects.registry.EffectRegistry;
import powercyphe.ultraeffects.effect.StyleMeterEffect;

public class StyleMeterHud implements HudLayerRegistrationCallback {
    public static final Identifier TEXTURE = UltraEffectsClient.id("textures/gui/sprites/hud/style_meter_background.png");

    @Override
    public void register(LayeredDrawerWrapper layeredDrawerWrapper) {
        layeredDrawerWrapper.attachLayerAfter(IdentifiedLayer.SCOREBOARD, UltraEffectsClient.id("style_meter"), ((context, tickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            TextRenderer textRenderer = client.textRenderer;


            StyleMeterEffect effect = EffectRegistry.STYLE_METER_EFFECT;
            ModConfig.StyleMeterPosition position = ModConfig.styleMeterPosition;


            if (effect.shouldDisplay()) {
                int x = position.isRight ? context.getScaledWindowWidth() + position.x : position.x;
                int y = position.isBottom ? context.getScaledWindowHeight() + position.y : position.y;

                String styleRank = effect.styleRank;
                int styleRankColor = effect.styleColor;

                String prefix = effect.getPrefix();
                String suffix = effect.getSuffix();

                context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x, y, 0, 0, 128, 104, 128, 104);

                context.getMatrices().push();
                context.getMatrices().translate(x + 3, y + 3, 0);
                context.getMatrices().scale(2F, 2F, 1F);
                context.drawText(textRenderer, Text.literal(prefix).formatted(Formatting.BOLD).withColor(styleRankColor), 0, 0, 0xFFFFFF, true);

                context.getMatrices().translate(prefix.length() * 7, 2, 0);
                context.getMatrices().scale(0.8F, 0.8F, 0.8F);
                context.drawText(textRenderer, Text.literal(suffix).formatted(Formatting.WHITE), 0, 0, 0xFFFFFF, true);
                context.getMatrices().pop();

                drawLine(context, x + 2, y + 21, styleRankColor, EffectRegistry.STYLE_METER_EFFECT.getLineMultiplier());

                int styleX = x + 2;
                int styleY = y + 15;

                for (Pair<Text, Integer> pair : effect.styleList) {
                    styleY += 11;
                    context.drawWrappedTextWithShadow(textRenderer, Text.literal("+ ").append(pair.getLeft().copy().formatted(Formatting.WHITE)), styleX, styleY, 120, 0x777777);
                }
            }
        }));
    }

    public static void drawLine(DrawContext context, int x, int y, int color, float lineMultiplier) {
        context.fill(x, y, x + 124, y + 2, 5, ColorHelper.fullAlpha(0x666666));
        context.fill(x, y, (int) (x + (124 * lineMultiplier)), y + 2, 10, ColorHelper.fullAlpha(color));

    }
}
