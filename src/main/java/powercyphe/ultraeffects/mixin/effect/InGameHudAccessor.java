package powercyphe.ultraeffects.mixin.effect;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(InGameHud.class)
public interface InGameHudAccessor {

    @Invoker("renderOverlay")
    void ultraeffects$renderOverlay(DrawContext context, Identifier texture, float opacity);
}
