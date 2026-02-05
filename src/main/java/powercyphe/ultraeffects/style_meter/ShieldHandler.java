package powercyphe.ultraeffects.style_meter;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import powercyphe.ultraeffects.UEConfig;
import powercyphe.ultraeffects.registry.ModSounds;
import powercyphe.ultraeffects.util.UEUtil;

public class ShieldHandler {
    public static void onHit(Player attacker, Entity target) {
        LocalPlayer clientPlayer = UEUtil.getLocalPlayer();

        if (clientPlayer == attacker && target instanceof LivingEntity livingEntity) {
            ItemStack blockStack = livingEntity.getItemBlockingWith();
            if (blockStack != null && blockStack.has(DataComponents.BLOCKS_ATTACKS)) {
                if (attacker.getSecondsToDisableBlocking() > 0) {
                    UEUtil.parryEffect(ModSounds.PARRY, UEConfig.parryShieldEnabled);
                    UEUtil.addStyle("shield_break", 140);
                }
            }
        }
    }
}