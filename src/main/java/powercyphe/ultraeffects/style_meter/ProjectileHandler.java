package powercyphe.ultraeffects.style_meter;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import powercyphe.ultraeffects.util.ComboHelper;
import powercyphe.ultraeffects.util.HitEntities;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

import java.util.List;

public class ProjectileHandler {
    public static void onHit(Projectile projectile, LivingEntity target, DamageSource source, float distanceTravelled) {
        LocalPlayer clientPlayer = UltraEffectsUtil.getLocalPlayer();

        if (projectile.getOwner() == clientPlayer) {
            HitEntities hitEntities = (HitEntities) projectile;
            List<Entity> entityList = hitEntities.ultraeffects$getHitEntities();
            if (target != null && !entityList.contains(target)) {
                entityList.add(target);
                hitEntities.ultraeffects$setHitEntities(hitEntities.ultraeffects$getHitEntities());
                ComboHelper.PROJECTILE.set(hitEntities.ultraeffects$getHitEntities().size()-1);
            }

            // Arrow
            if (projectile instanceof Arrow arrowEntity) {
                String type = "";
                int points = 60;

                if (distanceTravelled > 30) {
                    type = "far";
                    points = 100;
                } else if (arrowEntity.getDeltaMovement().length() < 1.6 / Mth.clamp(distanceTravelled / 10, 1, 4)) {
                    type = "slow";
                    points = 20;
                }
                UltraEffectsUtil.addStyle("bow_hit" + (type.isEmpty() ? "" : "_" + type), points);
            }
        }
    }

    public static boolean onDeath(Projectile projectile, LivingEntity target, DamageSource source, float distanceTravelled) {
        if (projectile instanceof FireworkRocketEntity firework || source.is(DamageTypes.FIREWORKS)) {
            UltraEffectsUtil.addStyle("kill_fireworks", 60);
            return true;
        }

        if (projectile instanceof ThrownTrident) {
            UltraEffectsUtil.addStyle("kill_trident", 60);
            return true;
        }

        return false;
    }
}
