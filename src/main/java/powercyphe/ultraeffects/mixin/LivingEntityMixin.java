package powercyphe.ultraeffects.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.ultraeffects.UltraEffectsClient;
import powercyphe.ultraeffects.effect.FlashEffect;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "onDamaged", at = @At("HEAD"))
    private void ultraeffects$maceHit(DamageSource source, CallbackInfo ci) {
        if (source.isOf(DamageTypes.MACE_SMASH) && UltraEffectsUtil.isClientPlayer(source.getAttacker())) {
            UltraEffectsUtil.playSound(UltraEffectsClient.PARRY, SoundCategory.PLAYERS, 0.5F, 1F);
            FlashEffect.display();
        }
    }
}
