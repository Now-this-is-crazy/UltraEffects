package powercyphe.ultraeffects.effect;

import net.minecraft.client.MinecraftClient;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.mixin.accessor.MinecraftClientAccessor;

public class FreezeEffect extends TickingEffect {

    public int freezeTicks, lastFreezeTicks = 0;

    @Override
    public void display() {
        MinecraftClient client = MinecraftClient.getInstance();
        client.getWindow().swapBuffers(((MinecraftClientAccessor) client).ultraeffects$getTracyFrameCapturer());

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
        return freezeTicks > 0 && freezeTicks < lastFreezeTicks && !MinecraftClient.getInstance().isPaused();
    }
}
