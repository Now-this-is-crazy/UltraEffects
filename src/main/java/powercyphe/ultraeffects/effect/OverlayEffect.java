package powercyphe.ultraeffects.effect;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;

public abstract class OverlayEffect extends TickingEffect {

    public static final String OVERLAY_PATH = "textures/misc/";
    private Identifier overlay;

    public abstract void render(DrawContext ctx, RenderTickCounter tickCounter);

    public void setOverlay(Identifier id) {
        this.overlay = id;
    }

    public void setRandomOverlay() {
        ArrayList<Identifier> overlays = new ArrayList<>(this.getAllOverlays());
        if (overlays.size() > 1) {
            overlays.remove(this.overlay);
        }
        setOverlay(overlays.get(Random.create().nextBetween(0, overlays.size()-1)));
    }

    public Identifier getOverlay() {
        return this.overlay == null ? Identifier.ofVanilla("misc/vignette") : this.overlay;
    }

    public abstract List<Identifier> getAllOverlays();

    public float getOpacity() {
        return 1.0F;
    }
}
