package powercyphe.ultraeffects.style_meter;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.registry.ModSounds;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public class DeathProtectionHandler {
    public static void onUse(Entity entity, DamageSource lastDamageSource) {
        LocalPlayer clientPlayer = UltraEffectsUtil.getLocalPlayer();
        if (entity != clientPlayer) {
            if (lastDamageSource != null && lastDamageSource.getDirectEntity() == clientPlayer) {
                UltraEffectsUtil.parryEffect(ModSounds.PARRY, ModConfig.parryDeathProtectorEnabled);
                UltraEffectsUtil.addStyle("deathprotector_use", 100);
            }
        }
    }
}
