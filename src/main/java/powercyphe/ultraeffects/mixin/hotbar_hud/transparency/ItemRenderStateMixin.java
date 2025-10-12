package powercyphe.ultraeffects.mixin.hotbar_hud.transparency;

import net.minecraft.client.render.item.ItemRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import powercyphe.ultraeffects.util.AlphaOverrideAddon;

@Mixin(ItemRenderState.class)
public class ItemRenderStateMixin implements AlphaOverrideAddon {

    @Unique
    private Float alphaOverride;

    @Override
    public void ultraeffects$setAlphaOverride(Float alpha) {
        this.alphaOverride = alpha;
    }

    @Override
    public Float ultraeffects$getAlphaOverride() {
        return this.alphaOverride;
    }
}
