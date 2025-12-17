package powercyphe.ultraeffects.mixin.hotbar_hud;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.hud.HotbarHud;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

@Mixin(ChatComponent.class)
public class ChatHudMixin {

    @ModifyExpressionValue(method = "render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Font;IIIZZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;guiHeight()I"))
    private int ultraeffects$hotbar_hud(int original) {
        return original + this.ultraeffects$getChatShift();
    }

    @Unique
    private int ultraeffects$getChatShift() {
        ChatComponent hud = (ChatComponent) (Object) this;
        Minecraft client = Minecraft.getInstance();
        LocalPlayer clientPlayer = UltraEffectsUtil.getLocalPlayer();

        if (HotbarHud.getRenderState().shouldShiftChat(client)) {
            return (clientPlayer.isCreative() ? 15 : 0) + switch (ModConfig.hotbarHudChatFocusModification) {
                case SHIFTED_CHAT -> -73;
                case null, default -> hud.isChatFocused() ? 0 : -73;
            };
        }
        return 0;
    }
}
