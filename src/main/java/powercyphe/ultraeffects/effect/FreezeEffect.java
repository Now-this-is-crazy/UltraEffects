package powercyphe.ultraeffects.effect;

import net.minecraft.client.Minecraft;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.mixin.accessor.MinecraftClientAccessor;

public class FreezeEffect extends TickingEffect {

    public int freezeTicks, lastFreezeTicks = 0;

    @Override
    public void display() {
        Minecraft client = Minecraft.getInstance();
        client.getWindow().updateDisplay(((MinecraftClientAccessor) client).ultraeffects$getTracyFrameCapture());

        freezeTicks = ModConfig.parryFreezeTicks + 1;
        lastFreezeTicks = freezeTicks;
    }

    @Override
    public void tick() {
        if (freezeTicks > 0) {
            freezeTicks--;
        }
    }

    public boolean shouldPause() {
        return freezeTicks > 0 && freezeTicks < lastFreezeTicks && !Minecraft.getInstance().isPaused();
    }
}
