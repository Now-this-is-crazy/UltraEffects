package powercyphe.ultraeffects.mixin.accessor;

import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InGameHud.class)
public interface InGameHudAccessor {

    @Accessor("heldItemTooltipFade")
    int ultraeffects$getHeldItemToolTipFade();
}
