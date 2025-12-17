package powercyphe.ultraeffects.mixin.accessor;

import com.mojang.blaze3d.TracyFrameCapture;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface MinecraftClientAccessor {

    @Accessor("tracyFrameCapture")
    TracyFrameCapture ultraeffects$getTracyFrameCapture();
}
