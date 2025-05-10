package powercyphe.ultraeffects.style_meter;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.registry.ModSounds;
import powercyphe.ultraeffects.util.ComboHelper;
import powercyphe.ultraeffects.util.DistanceTravelled;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public class DamageHandler {
    public static void onDamaged(Entity target, DamageSource source) {
        ClientPlayerEntity clientPlayer = UltraEffectsUtil.getClientPlayer();
        Entity attacker = source.getAttacker();

        if (target instanceof LivingEntity livingEntity) {

            // Projectile Handling
            if (source.getSource() instanceof ProjectileEntity projectile && source.isIn(DamageTypeTags.IS_PROJECTILE)) {
                ProjectileHandler.onHit(projectile, livingEntity, source, ((DistanceTravelled) projectile).ultraeffects$getDistanceTravelled());
                return;
            }

            // Lightning (Channeling)
            if (source.isIn(DamageTypeTags.IS_LIGHTNING)) {
                UltraEffectsUtil.addStyle("lightning_strike", 40);
            }

            // Melee Attacks
            if (attacker == clientPlayer && source.isDirect()) {
                ComboHelper.MELEE.increase(target);

                if (source.isOf(DamageTypes.MACE_SMASH)) {
                    UltraEffectsUtil.parryEffect(ModSounds.HAMMER_IMPACT, ModConfig.parryMaceEnabled, true);
                    UltraEffectsUtil.addStyle("mace_smash", 100);
                }
            }

        }
    }
}
