package powercyphe.ultraeffects.style_meter;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.registry.ModSounds;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public class ShieldHandler {
    public static void onHit(PlayerEntity attacker, Entity target) {
        ClientPlayerEntity clientPlayer = UltraEffectsUtil.getClientPlayer();

        if (clientPlayer == attacker && target instanceof LivingEntity livingEntity) {
            ItemStack blockStack = livingEntity.getBlockingItem();
            if (blockStack != null && blockStack.contains(DataComponentTypes.BLOCKS_ATTACKS)) {
                if (attacker.getWeaponDisableBlockingForSeconds() > 0) {
                    UltraEffectsUtil.parryEffect(ModSounds.PARRY, ModConfig.parryShieldEnabled);
                    UltraEffectsUtil.addStyle("shield_break", 140);
                }
            }
        }
    }
}