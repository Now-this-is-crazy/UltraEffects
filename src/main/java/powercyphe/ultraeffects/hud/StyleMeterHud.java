package powercyphe.ultraeffects.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.ARGB;
import powercyphe.ultraeffects.UEConfig;
import powercyphe.ultraeffects.hud.state.StyleMeterHudState;

public class StyleMeterHud extends UEHud<StyleMeterHudState> {

    @Override
    public StyleMeterHudState createState() {
        return new StyleMeterHudState();
    }

    @Override
    public void extract(StyleMeterHudState state, Minecraft client, float tickProgress) {
        state.backgroundColor = ARGB.color(
                (int) UEConfig.styleMeterBackgroundOpacity * 255,
                UEConfig.styleMeterBackgroundColorRed,
                UEConfig.styleMeterBackgroundColorGreen,
                UEConfig.styleMeterBackgroundColorBlue
        );
    }

    @Override
    public void submit(StyleMeterHudState state, GuiGraphics guiGraphics, Font font) {

    }

    @Override
    public boolean isVisible(StyleMeterHudState state) {
        return super.isVisible(state);
    }
}
