package powercyphe.ultraeffects.hud.state;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public record HotbarHudCursorRenderState(HotbarHudBarRenderState healthBar, HotbarHudBarRenderState absorptionBar, float healthFade, HotbarHudBarRenderState staminaBar, float staminaFade) {
    public static final HotbarHudCursorRenderState DEFAULT = new HotbarHudCursorRenderState(HotbarHudBarRenderState.DEFAULT, HotbarHudBarRenderState.DEFAULT, 0F, HotbarHudBarRenderState.DEFAULT, 0F);

    public void render(DrawContext context, TextRenderer textRenderer, float tickProgress) {
        int healthColor = ColorHelper.withAlpha(MathHelper.clamp(this.healthFade, 0, 1), this.healthBar.color());
        UltraEffectsUtil.renderCircleBar(context, context.getScaledWindowWidth() / 2, context.getScaledWindowHeight() / 2, this.healthBar.getProgress(tickProgress), 175, 170, 0, 0, healthColor);

        int absorptionColor = ColorHelper.withAlpha(MathHelper.clamp(this.healthFade, 0, 1), this.absorptionBar.color());
        UltraEffectsUtil.renderCircleBar(context, context.getScaledWindowWidth() / 2, context.getScaledWindowHeight() / 2, this.absorptionBar.getProgress(tickProgress), 175, (int) (170 * this.healthBar.getProgress(tickProgress)), 0, 0, absorptionColor);

        int staminaColor = ColorHelper.withAlpha(MathHelper.clamp(this.staminaFade, 0, 1), this.staminaBar.color());
        UltraEffectsUtil.renderCircleBar(context, context.getScaledWindowWidth() / 2, context.getScaledWindowHeight() / 2, this.staminaBar.getProgress(tickProgress), 185, -170, 2, 15, staminaColor);
    }

}
