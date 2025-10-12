package powercyphe.ultraeffects.mixin.death_sequence;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.hud.DeathScreenHud;
import powercyphe.ultraeffects.mixin.accessor.DeathScreenAccessor;

@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin extends Screen implements DeathScreenAccessor {

    @Shadow private int ticksSinceDeath;

    @Shadow protected abstract void setButtonsActive(boolean active);

    @Unique
    private final DeathScreenHud deathScreenHud = new DeathScreenHud();

    protected DeathScreenMixin(Text title) {
        super(title);
    }

    /**
     * <p><b>Button Repositioning</p></b>
     * Changes the Respawn and Title Screen buttons to
     * be at more optimal positions for the custom death screen
     **/

    @ModifyArgs(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;dimensions(IIII)Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;", ordinal = 0))
    private void ultraeffects$respawnButton(Args args) {
        if (ModConfig.deathScreenOverhaul) {
            args.set(0, 110);
            args.set(1, this.height - 36);
        }
    }

    @ModifyArgs(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;dimensions(IIII)Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;", ordinal = 1))
    private void ultraeffects$titleScreenButton(Args args) {
        if (ModConfig.deathScreenOverhaul) {
            args.set(0, this.width - 310);
            args.set(1, this.height - 36);
        }
    }

    /**
     * <p><b>Button Suppression</p></b>
     * Disables both the Respawn and Title Screen buttons
     * from working while the Death Sequence is running
     **/

    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/DeathScreen;setButtonsActive(Z)V"))
    private boolean ultraeffects$death_sequence(DeathScreen screen, boolean active) {
        return !ModConfig.deathScreenOverhaul;
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;tick()V"))
    private void ultraeffects$death_sequence(CallbackInfo ci) {
        if (ModConfig.deathScreenOverhaul && this.ticksSinceDeath > DeathScreenHud.SEQUENCE_TICKS) {
            this.setButtonsActive(true);
        }
    }

    /**
     * <p><b>Custom Rendering</p></b>
     * Renders the custom assets if the overhaul is enabled.
     **/

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;render(Lnet/minecraft/client/gui/DrawContext;IIF)V"))
    private boolean ultraeffects$death_sequence(Screen screen, DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (ModConfig.deathScreenOverhaul) {
            this.deathScreenHud.render(context, this.textRenderer, this.ticksSinceDeath);
            return this.ticksSinceDeath > DeathScreenHud.SEQUENCE_TICKS;
        }
        return true;
    }

    @ModifyArgs(method = "fillBackgroundGradient", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fillGradient(IIIIII)V"))
    private static void ultraeffects$death_sequence(Args args) {
        if (ModConfig.deathScreenOverhaul) {
            MinecraftClient client = MinecraftClient.getInstance();

            float alpha = 0.75F;
            if (client.currentScreen instanceof DeathScreen deathScreen) {
                alpha = (float) ((DeathScreenAccessor) deathScreen).ultraeffects$getTicksSinceDeath() / DeathScreenHud.SEQUENCE_TICKS * alpha;
            }

            args.set(4, ColorHelper.withAlpha(alpha, 0x000000));
            args.set(5, ColorHelper.withAlpha(alpha, 0x111111));
        }
    }

    /**
     * <p><b>Render Suppression</p></b>
     * Disables Rendering for some parts of the Death Screen
     * if the overhaul is enabled
     **/

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)V"))
    private boolean ultraeffects$death_sequence(DrawContext instance, TextRenderer textRenderer, Text text, int centerX, int y, int color) {
        return !ModConfig.deathScreenOverhaul;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;render(Lnet/minecraft/client/gui/DrawContext;IIF)V", shift = At.Shift.AFTER), cancellable = true)
    private void ultraeffects$death_sequence(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        if (ModConfig.deathScreenOverhaul && this.ticksSinceDeath > DeathScreenHud.SEQUENCE_TICKS) {
            ci.cancel();
        }
    }
}
