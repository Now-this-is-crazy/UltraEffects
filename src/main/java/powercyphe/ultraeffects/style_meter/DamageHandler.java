package powercyphe.ultraeffects.style_meter;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.registry.ModSounds;
import powercyphe.ultraeffects.util.ComboHelper;
import powercyphe.ultraeffects.util.DistanceTravelled;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public class DamageHandler {
    public static void onDamaged(Entity target, DamageSource source) {
        LocalPlayer clientPlayer = UltraEffectsUtil.getLocalPlayer();
        Entity attacker = source.getEntity();

        if (target instanceof LivingEntity livingEntity) {

            // Projectile Handling
            if (source.getDirectEntity() instanceof Projectile projectile && source.is(DamageTypeTags.IS_PROJECTILE)) {
                ProjectileHandler.onHit(projectile, livingEntity, source, ((DistanceTravelled) projectile).ultraeffects$getDistanceTravelled());
                return;
            }

            // Lightning (Channeling)
            if (source.is(DamageTypeTags.IS_LIGHTNING)) {
                UltraEffectsUtil.addStyle("lightning_strike", 40);
            }

            // Melee Attacks
            if (attacker == clientPlayer && source.isDirect()) {
                ComboHelper.MELEE.increase(target);

                if (source.is(DamageTypes.MACE_SMASH)) {
                    UltraEffectsUtil.parryEffect(ModSounds.HAMMER_IMPACT, ModConfig.parryMaceEnabled, true);
                    UltraEffectsUtil.addStyle("mace_smash", 100);
                }
            }

        }
    }
}
