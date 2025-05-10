package powercyphe.ultraeffects.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
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

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin extends Entity implements Ownable, DistanceTravelled, HitEntities {

    @Unique
    private int parryCooldown = -5;

    @Unique
    private float distanceTravelledClient = 0;

    @Unique
    private DefaultedList<Entity> hitEntities;

    public ProjectileEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(method = "tick", at = @At("TAIL"))
    private void ultraeffects$storeDistanceClient(CallbackInfo ci) {
        ProjectileEntity entity = (ProjectileEntity) (Object) this;
        if (entity.getWorld().isClient()) {
            this.distanceTravelledClient += (float) entity.getPos().distanceTo(new Vec3d(entity.lastX, entity.lastY, entity.lastZ));
        }
    }

    @Inject(method = "deflect", at = @At("HEAD"))
    private void ultraeffects$parry(ProjectileDeflection deflection, Entity deflector, Entity owner, boolean fromAttack, CallbackInfoReturnable<Boolean> cir) {
        if (UltraEffectsUtil.isClientPlayer(deflector) && fromAttack && deflection == ProjectileDeflection.REDIRECTED) {
            if ((this.parryCooldown + 5) < this.age) {
                this.parryCooldown = this.age;

                UltraEffectsUtil.parryEffect(ModSounds.PARRY, ModConfig.parryProjectilesEnabled);
                UltraEffectsUtil.addStyle("parry", 100);
            }
        }
    }

    @Override
    public void ultraeffects$setHitEntities(DefaultedList<Entity> hitEntities) {
        this.hitEntities = hitEntities;
    }

    @Override
    public DefaultedList<Entity> ultraeffects$getHitEntities() {
        if (this.hitEntities == null) {
            this.hitEntities = DefaultedList.of();
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