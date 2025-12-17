package powercyphe.ultraeffects.hud;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ARGB;
import net.minecraft.util.CommonColors;
import powercyphe.ultraeffects.UltraEffectsClient;
import powercyphe.ultraeffects.registry.ModSounds;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public class DeathScreenHud {

    public static final int SEQUENCE_TICKS = 40;

    private boolean shouldPlaySound = true;

    public void render(GuiGraphics context, Font textRenderer, int ticksSinceDeath) {
        LocalPlayer clientPlayer = UltraEffectsUtil.getLocalPlayer();

        if (ticksSinceDeath > SEQUENCE_TICKS) {
            context.fill(0, 0, context.guiWidth(), context.guiHeight(), CommonColors.BLACK);

            boolean talking = (ticksSinceDeath - 35) % 22 >= 11;
            Identifier texture = talking ? UltraEffectsClient.id("textures/death_sequence/death_skull_talking.png")
                    : UltraEffectsClient.id("textures/death_sequence/death_skull.png");
            context.blit(RenderPipelines.GUI_TEXTURED, texture, context.guiWidth() / 2 - 128, context.guiHeight() / 2 - 128, 0, 0, 256, 256, 256, 256);
            if (this.shouldPlaySound && talking && clientPlayer != null) {
                this.shouldPlaySound = false;
                UltraEffectsUtil.playSound(ModSounds.DEATH_SKULL, SoundSource.PLAYERS, 1F, 0.5F);
            } else if (!talking) {
                this.shouldPlaySound = true;
            }

            context.pose().pushMatrix();
            context.pose().translate(context.guiWidth() / 2F, 16F);
            context.pose().scale(3);

            context.drawCenteredString(textRenderer, Component.translatable("ultraeffects.death_sequence.text"), 0, 0, CommonColors.WHITE);
            context.pose().popMatrix();
        } else {
            if (clientPlayer != null) {
                if (this.shouldPlaySound && ticksSinceDeath == 1) {
                    this.shouldPlaySound = false;
                    UltraEffectsUtil.playSound(ModSounds.DEATH_SEQUENCE, SoundSource.PLAYERS, 1F, 0.5F);
                } else {
                    if (!this.shouldPlaySound && ticksSinceDeath == SEQUENCE_TICKS - 1) {
                        this.shouldPlaySound = true;

                    } else if (this.shouldPlaySound && ticksSinceDeath == SEQUENCE_TICKS) {
                        this.shouldPlaySound = false;
                        UltraEffectsUtil.playSound(ModSounds.DEATH_SEQUENCE_END, SoundSource.PLAYERS, 1F, 1F);
                    }
                }
            }

            int textY = 5;
            context.nextStratum();
            context.pose().pushMatrix();
            context.pose().scale(0.8F);
            for (int i = 1; i < Math.ceil(((double) ticksSinceDeath / SEQUENCE_TICKS) * 37.0); i++) {
                String text = Component.translatable("ultraeffects.death_sequence.line" + i).getString();

                boolean red = text.contains("<red>");
                boolean orange = text.contains("<orange>");
                if (red || orange) {
                    text = text.replace("<red>", "").replace("<orange>", "");
                }

                context.drawString(textRenderer, Component.literal(text).withStyle(ChatFormatting.BOLD), 5, textY, ARGB.opaque(red ? 0xff0102 : 0xffa502), true);
                textY += 10;
            }
            context.pose().popMatrix();
        }
    }
}
