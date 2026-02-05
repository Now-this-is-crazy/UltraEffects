package powercyphe.ultraeffects.event;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import powercyphe.ultraeffects.hud.UEHud;
import powercyphe.ultraeffects.registry.UERegistries;

public class UEHudRenderer implements HudElement {

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft client = Minecraft.getInstance();
        this.render(client, guiGraphics, client.font, deltaTracker.getGameTimeDeltaPartialTick(false));
    }

    public void render(Minecraft client, GuiGraphics guiGraphics, Font font, float tickProgress) {
        for (UEHud<?> hud : UERegistries.HUD) {
            hud.extract(client, tickProgress);

            if (hud.isVisible()) {
                hud.submit(guiGraphics, font);
            }
        }
    }
}
