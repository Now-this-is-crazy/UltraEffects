package powercyphe.ultraeffects.mixin.accessor;

import net.minecraft.client.gui.screens.DeathScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DeathScreen.class)
public interface DeathScreenAccessor {

    @Accessor("delayTicker")
    int ultraeffects$getTicksSinceDeath();
}
