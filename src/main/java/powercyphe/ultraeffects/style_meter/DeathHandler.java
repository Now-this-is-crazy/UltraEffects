package powercyphe.ultraeffects.style_meter;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.TridentItem;
import powercyphe.ultraeffects.util.ComboHelper;
import powercyphe.ultraeffects.util.DistanceTravelled;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public class DeathHandler {
    public static void onDeath(LivingEntity target, DamageSource source) {
        LocalPlayer clientPlayer = UltraEffectsUtil.getLocalPlayer();
        if (source != null && target != clientPlayer) {

            if ((source.getEntity() == UltraEffectsUtil.getLocalPlayer() || source.getDirectEntity() == UltraEffectsUtil.getLocalPlayer())) {
                boolean bl = ComboHelper.KILL.increase(target);

                if (!bl) {
                    if (source.getDirectEntity() instanceof Projectile projectile) {
                        if (ProjectileHandler.onDeath(projectile, target, source, ((DistanceTravelled) projectile).ultraeffects$getDistanceTravelled())) {
                            return;
                        }
                    }

                    String type = "";
                    float points = 70;
                    if (source.is(DamageTypeTags.IS_EXPLOSION)) {
                        type = "explosion";
                        points = 60;
                    }
                    if (source.getWeaponItem() != null) {
                        if (source.getWeaponItem().is(ItemTags.AXES)) {
                            type = "axe";
                            points = 80;
                        }
                        if (source.getWeaponItem().getItem() instanceof TridentItem) {
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
