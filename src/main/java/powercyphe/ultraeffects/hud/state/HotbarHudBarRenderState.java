package powercyphe.ultraeffects.hud.state;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import org.joml.Math;
import powercyphe.ultraeffects.hud.HotbarHud;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public record HotbarHudBarRenderState(float currentValue, float smoothedCurrent, float smoothedPrevious, float maxValue, boolean hasOverlay, int overlayTick, float additionalValue, int width, int height, int cuts, int cutWidth, int color, int backgroundColor) {
    public static final HotbarHudBarRenderState DEFAULT = new HotbarHudBarRenderState(0, 0, 0, 1, 0, 0, 0, 0, -1, -1);

    public HotbarHudBarRenderState(float currentValue, float smoothedValue, float smoothedPrevious, float maxValue, int width, int height, int cuts, int cutWidth, int color, int backgroundColor) {
        this(currentValue, smoothedValue, smoothedPrevious, maxValue, false, 0, 0, width, height, cuts, cutWidth, color, backgroundColor);
    }

    public HotbarHudBarRenderState(HotbarHud.BarType barType, boolean hasOverlay, int overlayTick, float additionalValue, int width, int height, int cuts, int cutWidth) {
        this(barType.current, barType.smoothedCurrent, barType.smoothedPrevious, barType.max, hasOverlay, overlayTick, additionalValue, width, height, cuts, cutWidth, barType.getColor(),
                ARGB.setBrightness(barType.getColor(), 0.25F));
    }

    public HotbarHudBarRenderState(HotbarHud.BarType barType, int width, int height, int cuts, int cutWidth) {
        this(barType, false, 0, 0, width, height, cuts, cutWidth);
    }

    // Rendering
    public void render(HotbarHudRenderState state, GuiGraphics context, int x, int y, float tickProgress, boolean hasBackground) {
        this.renderBaseBar(state, context, x, y, tickProgress, hasBackground);
        if (this.hasOverlay) {
            this.renderOverlayBar(state, context, x, y, tickProgress);
        }
    }

    private void renderBaseBar(HotbarHudRenderState state, GuiGraphics context, int x, int y, float tickProgress, boolean hasBackground) {
        if (hasBackground) {
            UltraEffectsUtil.renderHorizontalColoredBar(context, x, y, 1, this.width, this.height, state.adjustColor(this.backgroundColor), this.cuts, this.cutWidth);
        }
        UltraEffectsUtil.renderHorizontalColoredBar(context, x, y, this.getProgress(tickProgress), this.width, this.height, state.adjustColor(this.color), this.cuts, this.cutWidth);
    }

    private void renderOverlayBar(HotbarHudRenderState state, GuiGraphics context, int x, int y, float tickProgress) {
        tickProgress = tickProgress * (this.overlayTick > 10 ? -1 : 1);

        float alpha = Math.abs(Math.sin(Math.toRadians((this.overlayTick + tickProgress) / 50F * 360F)));
        int overlayColor = state.adjustColor(ARGB.color(Mth.lerp(alpha, 0F, 0.5F), this.color));

        UltraEffectsUtil.renderHorizontalColoredBar(context, x, y, this.getOverlayProgress(tickProgress), this.width, this.height, overlayColor, this.cuts, this.cutWidth);
    }

    // Helper Methods
    public float getOverlayProgress(float tickProgress) {
        return Mth.clamp((this.getProgress(tickProgress) + this.additionalValue) / this.maxValue, 0, 1);
    }

    public float getCurrent(float tickProgress) {
        return Mth.lerp(tickProgress, this.smoothedPrevious, this.smoothedCurrent);
    }

    public float getProgress(float tickProgress) {
        return Mth.clamp(Mth.lerp(tickProgress, this.smoothedPrevious, this.smoothedCurrent) / this.maxValue, 0, 1);
    }
}
