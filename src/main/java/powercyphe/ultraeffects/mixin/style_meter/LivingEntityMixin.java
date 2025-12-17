package powercyphe.ultraeffects.mixin.style_meter;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.ultraeffects.style_meter.DamageHandler;
import powercyphe.ultraeffects.style_meter.DeathHandler;
import powercyphe.ultraeffects.util.LastDamageSource;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements LastDamageSource {

    @Unique
    private DamageSource lastDamageSource = null;

    @Inject(method = "handleDamageEvent", at = @At("HEAD"))
    private void ultraeffects$combo(DamageSource source, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        this.lastDamageSource = source;
        DamageHandler.onDamaged(entity, source);
    }

    @Inject(method = "handleEntityEvent", at = @At("HEAD"))
    private void ultraeffects$onDeath(byte status, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (status == 3) {
            DeathHandler.onDeath(entity, ((LastDamageSource) entity).ultraeffects$getLastDamageSource());
        }
    }

    @Override
    public void ultraeffects$setLastDamageSource(DamageSource source) {
        this.lastDamageSource = source;
    }

    @Override
    public DamageSource ultraeffects$getLastDamageSource() {
        return this.lastDamageSource;
    }
}
