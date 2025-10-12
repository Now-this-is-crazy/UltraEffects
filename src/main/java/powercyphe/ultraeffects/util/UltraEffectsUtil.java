package powercyphe.ultraeffects.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.joml.Math;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.UltraEffectsClient;
import powercyphe.ultraeffects.effect.OverlayEffect;
import powercyphe.ultraeffects.registry.EffectRegistry;
import powercyphe.ultraeffects.sound.UnstoppableSoundInstance;

import java.util.List;

public class UltraEffectsUtil {

    public static void renderHorizontalColoredBar(DrawContext context, int x, int y, float progress, int fullWidth, int height, int color, int cuts, int cutWidth) {
        if (progress > 0) {
            DefaultedList<Integer> cutPositions = DefaultedList.of();
            if (cuts > 0 && cuts * cutWidth * 2 <= fullWidth) {
                float spacing = (float) fullWidth / (cuts + 1);
                for (int i1 = 1; i1 <= cuts; i1++) {
                    int cutStart = x + (int) Math.ceil(i1 * spacing - cutWidth / 2f);
                    for (int i2 = 0; i2 < cutWidth; i2++) {
                        cutPositions.add(cutStart + i2);
                    }
                }
            }

            int width = (int) (progress * fullWidth);

            int xStart = Math.min(x, x + width);
            int xEnd = Math.max(x, x + width);

            int topHeight = (int) Math.ceil((double) height / 2);
            int bottomHeight = (int) Math.floor((double) height / 2);

            while (x <= xEnd) {
                if (!cutPositions.contains(x)) {
                    if (x - xStart < height / 3 || xEnd - x < height / 3) {
                        int distance = height / 3 - Math.min(Math.abs(x - xStart), Math.abs(xEnd - x));
                        context.fill(x, y - (bottomHeight - distance), x + 1, y + (topHeight - distance), color);

                    } else {
                        context.fill(x, y - bottomHeight, x + 1, y + topHeight, color);
                    }
                }
                x++;
            }
        }
    }

    public static void renderRoundBox(DrawContext context, int x1, int y1, int x2, int y2, int color) {
        if (Math.abs(x2-x1) < 3 || Math.abs(y2-y1) < 3) {
            context.fill(x1, y1, x2, y2, color);
        } else {
            int boxXMin = Math.min(x1, x2);
            int boxXMax = Math.max(x1, x2);

            int boxYMin = Math.min(y1, y2);
            int boxYMax = Math.max(y1, y2);

            context.fill(boxXMin + 1, boxYMin + 1, boxXMax - 1, boxYMax - 1, color);

            context.fill(boxXMin + 2, boxYMin, boxXMax - 2, boxYMin + 1, color);
            context.fill(boxXMin + 2, boxYMax, boxXMax - 2, boxYMax - 1, color);

            context.fill(boxXMin, boxYMin + 2, boxXMin + 1, boxYMax - 2, color);
            context.fill(boxXMax, boxYMin + 2, boxXMax - 1, boxYMax - 2, color);
        }
    }

    public static void renderCircleBar(DrawContext context, int x, int y, float progress, int startAngle, int angleLength, int cuts, int cutWidth, int color) {
        if (progress > 0) {
            Vec3d vec = new Vec3d(0, 6, 0);
            vec = vec.rotateZ(Math.toRadians(-startAngle));

            DefaultedList<Integer> cutAngles = DefaultedList.of();
            if (cuts > 0 && cuts * cutWidth <= Math.abs(angleLength)) {
                float spacing = (float) Math.abs(angleLength) / (cuts + 1);
                for (int i = 1; i <= cuts; i++) {
                    int centerAngle = Math.round(i * spacing);
                    int startCut = centerAngle - (cutWidth / 2);
                    for (int j = 0; j < cutWidth; j++) {
                        cutAngles.add(startCut + j);
                    }
                }
            }

            int direction = angleLength >= 0 ? 1 : -1;
            int steps = (int) Math.abs((float) angleLength * progress);

            for (int i = 0; i <= steps; i++) {
                if (!cutAngles.contains(i)) {
                    float circleX = (float) vec.getX();
                    float circleY = (float) vec.getY();

                    context.getMatrices().pushMatrix();
                    context.getMatrices().translate(x + circleX - 0.5F, y + circleY - 0.5F);
                    context.getMatrices().scale(1 / 5F);

                    context.fill(0, 0, (int) circleX + (circleX >= 0 ? 1 : -1), (int) circleY + (circleY >= 0 ? 1 : -1), color);

                    context.getMatrices().popMatrix();
                }

                vec = vec.rotateZ(Math.toRadians(direction));
            }
        }
    }

    public static void addStyle(String id, float points) {
        EffectRegistry.STYLE_METER_EFFECT.addStyle(Text.translatable("ultraeffects.style_meter.style." + id), points);
    }

    public static void addStyle(Text text, float points) {
        EffectRegistry.STYLE_METER_EFFECT.addStyle(text, points);
    }

    public static List<Identifier> stringToIdentifierList(List<String> list) {
        DefaultedList<Identifier> available = DefaultedList.of();
        for (String str : list) {
            available.add(UltraEffectsClient.id(OverlayEffect.OVERLAY_PATH + str + ".png"));
        }
        return available;
    }

    public static void renderOverlay(DrawContext ctx, Identifier texture, float opacity) {
        int i = ColorHelper.getWhite(opacity);
        ctx.drawTexture(RenderPipelines.GUI_TEXTURED, texture, 0, 0, 0.0F, 0.0F, ctx.getScaledWindowWidth(), ctx.getScaledWindowHeight(), ctx.getScaledWindowWidth(), ctx.getScaledWindowHeight(), i);
    }

    public static void setAlphaOverride(DrawContext context, float alphaOverride) {
        ((AlphaOverrideAddon) context).ultraeffects$setAlphaOverride(alphaOverride);
    }

    public static void resetAlphaOverride(DrawContext context) {
        ((AlphaOverrideAddon) context).ultraeffects$setAlphaOverride(null);
    }

    public static void parryEffect(SoundEvent sound, boolean configCondition) {
        parryEffect(sound, configCondition, false);
    }

    public static void parryEffect(SoundEvent sound, boolean configCondtion, boolean disableForTick) {
        if (!configCondtion || UltraEffectsClient.PARRY_DISABLED) {
            return;
        }
        if (disableForTick) {
            UltraEffectsClient.PARRY_DISABLED = true;
        }
        UltraEffectsUtil.playSound(sound, SoundCategory.PLAYERS, 0.9F + Random.create().nextInt(4) * 0.05F, 1F);
        EffectRegistry.FREEZE_EFFECT.display();
        EffectRegistry.FLASH_EFFECT.display();
    }

    public static boolean isRunningDeathScreenOverhaul() {
        return MinecraftClient.getInstance().currentScreen instanceof DeathScreen && ModConfig.deathScreenOverhaul;
    }

    public static boolean isClientPlayer(Entity entity) {
        MinecraftClient client = MinecraftClient.getInstance();
        return client != null && client.player == entity;
    }

    public static boolean isSpectator() {
        ClientPlayerEntity clientPlayer = getClientPlayer();

        if (clientPlayer != null) {
            return clientPlayer.isSpectator();
        }
        return false;
    }

    public static ClientPlayerEntity getClientPlayer() {
        return MinecraftClient.getInstance().player;
    }

    public static void playSound(SoundEvent soundEvent, SoundCategory soundCategory, float pitch, float volume) {
        MinecraftClient.getInstance().getSoundManager().play(
                new UnstoppableSoundInstance(soundEvent, soundCategory, pitch, volume)
        );
    }
}
