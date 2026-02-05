package powercyphe.ultraeffects.style_meter;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import powercyphe.ultraeffects.UEConfig;
import powercyphe.ultraeffects.registry.ModSounds;
import powercyphe.ultraeffects.util.UEUtil;

public class DeathProtectionHandler {
    public static void onUse(Entity entity, DamageSource lastDamageSource) {
        LocalPlayer clientPlayer = UEUtil.getLocalPlayer();
        if (entity != clientPlayer) {
            if (lastDamageSource != null && lastDamageSource.getDirectEntity() == clientPlayer) {
                UEUtil.parryEffect(ModSounds.PARRY, UEConfig.parryDeathProtectorEnabled);
                UEUtil.addStyle("deathprotector_use", 100);
            }
        }
    }
}
