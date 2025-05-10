package powercyphe.ultraeffects.style_meter;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import powercyphe.ultraeffects.util.ComboHelper;
import powercyphe.ultraeffects.util.HitEntities;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

import java.util.List;

public class ProjectileHandler {
    public static void onHit(ProjectileEntity projectile, LivingEntity target, DamageSource source, float distanceTravelled) {
        ClientPlayerEntity clientPlayer = UltraEffectsUtil.getClientPlayer();

        if (projectile.getOwner() == clientPlayer) {
            HitEntities hitEntities = (HitEntities) projectile;
            List<Entity> entityList = hitEntities.ultraeffects$getHitEntities();
            if (target != null && !entityList.contains(target)) {
                entityList.add(target);
                hitEntities.ultraeffects$setHitEntities(hitEntities.ultraeffects$getHitEntities());
                ComboHelper.PROJECTILE.set(hitEntities.ultraeffects$getHitEntities().size()-1);
            }

            // Arrow
            if (projectile instanceof ArrowEntity arrowEntity) {
                String type = "";
                int points = 60;

                if (distanceTravelled > 30) {
                    type = "far";
                    points = 100;
                } else if (arrowEntity.getVelocity().length() < 1.6 / Math.clamp(distanceTravelled / 10, 1, 4)) {
                    type = "slow";
                    points = 20;
                }
                UltraEffectsUtil.addStyle("bow_hit" + (type.isEmpty() ? "" : "_" + type), points);
            }
        }
    }

    public static boolean onDeath(ProjectileEntity projectile, LivingEntity target, DamageSource source, float distanceTravelled) {
        if (projectile instanceof FireworkRocketEntity firework || source.isOf(DamageTypes.FIREWORKS)) {
            UltraEffectsUtil.addStyle("kill_fireworks", 60);
            return true;
        }

        if (projectile instanceof TridentEntity) {
            UltraEffectsUtil.addStyle("kill_trident", 60);
            return true;
        }

        return false;
    }
}
