package powercyphe.ultraeffects.mixin.style_meter;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.ultraeffects.style_meter.ShieldHandler;

@Mixin(MultiPlayerGameMode.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;attack(Lnet/minecraft/world/entity/Entity;)V"))
    private void ultraeffects$onShieldHit(Player player, Entity target, CallbackInfo ci) {
        if (target instanceof LivingEntity livingEntity && livingEntity.isBlocking() && !livingEntity.isInvulnerable()) {
            ShieldHandler.onHit(player, target);
        }
    }
}
