package powercyphe.ultraeffects.style_meter;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.registry.ModSounds;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public class DeathProtectionHandler {
    public static void onUse(Entity entity, DamageSource lastDamageSource) {
        ClientPlayerEntity clientPlayer = UltraEffectsUtil.getClientPlayer();
        if (entity != clientPlayer) {
            if (lastDamageSource != null && lastDamageSource.getSource() == clientPlayer) {
                UltraEffectsUtil.parryEffect(ModSounds.PARRY, ModConfig.parryDeathProtectorEnabled);
                UltraEffectsUtil.addStyle("deathprotector_use", 100);
            }
        }
    }
}
