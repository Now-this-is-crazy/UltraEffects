package powercyphe.ultraeffects.hud;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import powercyphe.ultraeffects.UltraEffectsClient;
import powercyphe.ultraeffects.registry.ModSounds;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public class DeathScreenHud {

    public static final int SEQUENCE_TICKS = 40;

    private boolean shouldPlaySound = true;

    public void render(DrawContext context, TextRenderer textRenderer, int ticksSinceDeath) {
        ClientPlayerEntity clientPlayer = UltraEffectsUtil.getClientPlayer();

        if (ticksSinceDeath > SEQUENCE_TICKS) {
            context.fill(0, 0, context.getScaledWindowWidth(), context.getScaledWindowHeight(), Colors.BLACK);

            boolean talking = (ticksSinceDeath - 35) % 22 >= 11;
            Identifier texture = talking ? UltraEffectsClient.id("textures/death_sequence/death_skull_talking.png")
                    : UltraEffectsClient.id("textures/death_sequence/death_skull.png");
            context.drawTexture(RenderPipelines.GUI_TEXTURED, texture, context.getScaledWindowWidth() / 2 - 128, context.getScaledWindowHeight() / 2 - 128, 0, 0, 256, 256, 256, 256);
            if (this.shouldPlaySound && talking && clientPlayer != null) {
                this.shouldPlaySound = false;
                UltraEffectsUtil.playSound(ModSounds.DEATH_SKULL, SoundCategory.PLAYERS, 1F, 0.5F);
            } else if (!talking) {
                this.shouldPlaySound = true;
            }

            context.getMatrices().pushMatrix();
            context.getMatrices().translate(context.getScaledWindowWidth() / 2F, 16F);
            context.getMatrices().scale(3);

            context.drawCenteredTextWithShadow(textRenderer, Text.translatable("ultraeffects.death_sequence.text"), 0, 0, Colors.WHITE);
            context.getMatrices().popMatrix();
        } else {
            if (clientPlayer != null) {
                if (this.shouldPlaySound && ticksSinceDeath == 1) {
                    this.shouldPlaySound = false;
                    UltraEffectsUtil.playSound(ModSounds.DEATH_SEQUENCE, SoundCategory.PLAYERS, 1F, 0.5F);
                } else {
                    if (!this.shouldPlaySound && ticksSinceDeath == SEQUENCE_TICKS - 1) {
                        this.shouldPlaySound = true;

                    } else if (this.shouldPlaySound && ticksSinceDeath == SEQUENCE_TICKS) {
                        this.shouldPlaySound = false;
                        UltraEffectsUtil.playSound(ModSounds.DEATH_SEQUENCE_END, SoundCategory.PLAYERS, 1F, 1F);
                    }
                }
            }

            int textY = 5;
            context.createNewRootLayer();
            context.getMatrices().pushMatrix();
            context.getMatrices().scale(0.8F);
            for (int i = 1; i < Math.ceil(((double) ticksSinceDeath / SEQUENCE_TICKS) * 37.0); i++) {
                String text = Text.translatable("ultraeffects.death_sequence.line" + i).getString();

                boolean red = text.contains("<red>");
                boolean orange = text.contains("<orange>");
                if (red || orange) {
                    text = text.replace("<red>", "").replace("<orange>", "");
                }

                context.drawText(textRenderer, Text.literal(text).formatted(Formatting.BOLD), 5, textY, ColorHelper.fullAlpha(red ? 0xff0102 : 0xffa502), true);
                textY += 10;
            }
            context.getMatrices().popMatrix();
        }
    }
}
