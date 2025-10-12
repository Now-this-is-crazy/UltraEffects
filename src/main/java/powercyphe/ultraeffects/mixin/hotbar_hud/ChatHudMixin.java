package powercyphe.ultraeffects.mixin.hotbar_hud;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.hud.HotbarHud;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

@Mixin(ChatHud.class)
public class ChatHudMixin {

    @Shadow @Final private MinecraftClient client;

    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;getScaledWindowHeight()I"))
    private int ultraeffects$hotbar_hud(int original) {
        return original + this.getChatShift();
    }

    @ModifyExpressionValue(method = "toChatLineY", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;getScaledHeight()I"))
    private int ultraeffects$hotbar_hud2(int chatY) {
        return chatY + this.getChatShift();
    }

    @Unique
    private int getChatShift() {
        ChatHud hud = (ChatHud) (Object) this;
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity clientPlayer = UltraEffectsUtil.getClientPlayer();

        if (HotbarHud.getRenderState().shouldShiftChat(client)) {
            return (clientPlayer.isCreative() ? 15 : 0) + switch (ModConfig.hotbarHudChatFocusModification) {
                case SHIFTED_CHAT -> -73;
                case null, default -> hud.isChatFocused() ? 0 : -73;
            };
        }
        return 0;
    }
}
