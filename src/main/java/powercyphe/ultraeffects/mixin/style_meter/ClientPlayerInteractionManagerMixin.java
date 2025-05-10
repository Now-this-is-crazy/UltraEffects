package powercyphe.ultraeffects.mixin.style_meter;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.ultraeffects.style_meter.ShieldHandler;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(method = "attackEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;attack(Lnet/minecraft/entity/Entity;)V"))
    private void ultraeffects$onShieldHit(PlayerEntity player, Entity target, CallbackInfo ci) {
        if (target instanceof LivingEntity livingEntity && livingEntity.isBlocking() && !livingEntity.isInvulnerable()) {
            ShieldHandler.onHit(player, target);
        }
    }
}
