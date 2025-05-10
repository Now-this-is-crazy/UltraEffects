package powercyphe.ultraeffects.style_meter;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.ItemTags;
import powercyphe.ultraeffects.util.ComboHelper;
import powercyphe.ultraeffects.util.DistanceTravelled;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public class DeathHandler {
    public static void onDeath(LivingEntity target, DamageSource source) {
        ClientPlayerEntity clientPlayer = UltraEffectsUtil.getClientPlayer();
        if (source != null && target != clientPlayer) {

            if ((source.getAttacker() == UltraEffectsUtil.getClientPlayer() || source.getSource() == UltraEffectsUtil.getClientPlayer())) {
                boolean bl = ComboHelper.KILL.increase(target);

                if (!bl) {
                    if (source.getSource() instanceof ProjectileEntity projectile) {
                        if (ProjectileHandler.onDeath(projectile, target, source, ((DistanceTravelled) projectile).ultraeffects$getDistanceTravelled())) {
                            return;
                        }
                    }

                    String type = "";
                    float points = 70;
                    if (source.isIn(DamageTypeTags.IS_EXPLOSION)) {
                        type = "explosion";
                        points = 60;
                    }
                    if (source.getWeaponStack() != null) {
                        if (source.getWeaponStack().isIn(ItemTags.AXES)) {
                            type = "axe";
                            points = 80;
                        }
                        if (source.getWeaponStack().getItem() instanceof TridentItem) {
                            type = "trident";
                            points = 80;
                        }
                    }
                    UltraEffectsUtil.addStyle("kill" + (type.isEmpty() ? "" : "_" + type), points);
                    return;

                }
            }
        }
    }
}
