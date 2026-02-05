package powercyphe.ultraeffects.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public abstract class UEHud<S extends UEHudState> {
    private final S state = this.createState();

    public abstract S createState();

    public final void extract(Minecraft client, float tickProgress) {
        this.extract(this.state, client, tickProgress);
    }

    public abstract void extract(S state, Minecraft client, float tickProgress);

    public final void submit(GuiGraphics guiGraphics, Font font) {
        this.submit(this.state, guiGraphics, font);
    }

    public abstract void submit(S state, GuiGraphics guiGraphics, Font font);

    public boolean isVisible() {
        return this.isVisible(this.state);
    }

    public boolean isVisible(S state) {
        return true;
    }
}
