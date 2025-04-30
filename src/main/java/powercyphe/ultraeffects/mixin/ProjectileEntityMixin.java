package powercyphe.ultraeffects.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import powercyphe.ultraeffects.UltraEffectsClient;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin extends Entity implements Ownable {

    @Unique
    private int parryCooldown = -5;

    public ProjectileEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "deflect", at = @At("HEAD"))
    private void ultraeffects$parry(ProjectileDeflection deflection, Entity deflector, Entity owner, boolean fromAttack, CallbackInfoReturnable<Boolean> cir) {
        if (UltraEffectsUtil.isClientPlayer(deflector) && fromAttack && (this.parryCooldown + 5) < this.age) {
            if (deflection == ProjectileDeflection.REDIRECTED) {
                this.parryCooldown = this.age;

                UltraEffectsUtil.playSound(UltraEffectsClient.PARRY, SoundCategory.PLAYERS, 1F ,1F);
                UltraEffectsUtil.parryEffect();
            }
        }
    }
}