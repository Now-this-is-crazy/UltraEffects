package powercyphe.ultraeffects.hud.state;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public record HotbarHudCursorRenderState(HotbarHudBarRenderState healthBar, HotbarHudBarRenderState absorptionBar, float healthFade, HotbarHudBarRenderState staminaBar, float staminaFade) {
    public static final HotbarHudCursorRenderState DEFAULT = new HotbarHudCursorRenderState(HotbarHudBarRenderState.DEFAULT, HotbarHudBarRenderState.DEFAULT, 0F, HotbarHudBarRenderState.DEFAULT, 0F);

    public void render(GuiGraphics context, Font textRenderer, float tickProgress) {
        int healthColor = ARGB.color(Mth.clamp(this.healthFade, 0, 1), this.healthBar.color());
        UltraEffectsUtil.renderCircleBar(context, context.guiWidth() / 2, context.guiHeight() / 2, this.healthBar.getProgress(tickProgress), 175, 170, 0, 0, healthColor);

        int absorptionColor = ARGB.color(Mth.clamp(this.healthFade, 0, 1), this.absorptionBar.color());
        UltraEffectsUtil.renderCircleBar(context, context.guiWidth() / 2, context.guiHeight() / 2, this.absorptionBar.getProgress(tickProgress), 175, (int) (170 * this.healthBar.getProgress(tickProgress)), 0, 0, absorptionColor);

        int staminaColor = ARGB.color(Mth.clamp(this.staminaFade, 0, 1), this.staminaBar.color());
        UltraEffectsUtil.renderCircleBar(context, context.guiWidth() / 2, context.guiHeight() / 2, this.staminaBar.getProgress(tickProgress), 185, -170, 2, 15, staminaColor);
    }

}
