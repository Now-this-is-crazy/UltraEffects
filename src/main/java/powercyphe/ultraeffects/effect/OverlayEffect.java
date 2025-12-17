package powercyphe.ultraeffects.effect;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.List;

public abstract class OverlayEffect extends TickingEffect {

    public static final String OVERLAY_PATH = "textures/misc/";
    private Identifier overlay;

    public abstract void render(GuiGraphics ctx, DeltaTracker tickCounter);

    public void setOverlay(Identifier id) {
        this.overlay = id;
    }

    public void setRandomOverlay() {
        ArrayList<Identifier> overlays = new ArrayList<>(this.getAllOverlays());
        if (overlays.size() > 1) {
            overlays.remove(this.overlay);
        }
        setOverlay(overlays.get(RandomSource.create().nextIntBetweenInclusive(0, overlays.size()-1)));
    }

    public Identifier getOverlay() {
        return this.overlay == null ? Identifier.withDefaultNamespace("misc/vignette") : this.overlay;
    }

    public abstract List<Identifier> getAllOverlays();

    public float getOpacity() {
        return 1.0F;
    }
}
