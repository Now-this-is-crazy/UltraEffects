package powercyphe.ultraeffects.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.registry.ModSounds;
import powercyphe.ultraeffects.util.DistanceTravelled;
import powercyphe.ultraeffects.util.HitEntities;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

@Mixin(Projectile.class)
public abstract class ProjectileEntityMixin extends Entity implements TraceableEntity, DistanceTravelled, HitEntities {

    @Unique
    private int parryCooldown = -5;

    @Unique
    private float distanceTravelledClient = 0;

    @Unique
    private NonNullList<Entity> hitEntities;

    public ProjectileEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void ultraeffects$storeDistanceClient(CallbackInfo ci) {
        Projectile entity = (Projectile) (Object) this;
        if (entity.level().isClientSide()) {
            this.distanceTravelledClient += (float) entity.position().distanceTo(new Vec3(entity.xo, entity.yo, entity.zo));
        }
    }

    @Inject(method = "deflect", at = @At("HEAD"))
    private void ultraeffects$parry(ProjectileDeflection deflection, Entity deflector, EntityReference<Entity> lazyEntityReference, boolean fromAttack, CallbackInfoReturnable<Boolean> cir) {
        if (UltraEffectsUtil.isLocalPlayer(deflector) && fromAttack && deflection == ProjectileDeflection.AIM_DEFLECT) {
            if ((this.parryCooldown + 5) < this.tickCount) {
                this.parryCooldown = this.tickCount;

                UltraEffectsUtil.parryEffect(ModSounds.PARRY, ModConfig.parryProjectilesEnabled);
                UltraEffectsUtil.addStyle("parry", 100);
            }
        }
    }

    @Override
    public void ultraeffects$setHitEntities(NonNullList<Entity> hitEntities) {
        this.hitEntities = hitEntities;
    }

    @Override
    public NonNullList<Entity> ultraeffects$getHitEntities() {
        if (this.hitEntities == null) {
            this.hitEntities = NonNullList.create();
        }
        return this.hitEntities;
    }

    @Override
    public void ultraeffects$setDistanceTravelled(float distanceTravelled) {
        this.distanceTravelledClient = distanceTravelled;
    }

    @Override
    public float ultraeffects$getDistanceTravelled() {
        return this.distanceTravelledClient;
    }
}