package powercyphe.ultraeffects.mixin.accessor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.tracy.TracyFrameCapturer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {

    @Accessor("tracyFrameCapturer")
    TracyFrameCapturer ultraeffects$getTracyFrameCapturer();
}
