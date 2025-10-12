package powercyphe.ultraeffects.hud.state;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

import java.util.Optional;

public class HotbarHudRenderState {
    public int x = 0;
    public int y = 0;

    public int overlayTick = 0;
    public int heldItemTooltipFade = 0;
    public boolean chatFocused = false;

    public HotbarHudBarRenderState healthBar = HotbarHudBarRenderState.DEFAULT;
    public HotbarHudBarRenderState absorptionBar = HotbarHudBarRenderState.DEFAULT;
    public HotbarHudBarRenderState staminaBar = HotbarHudBarRenderState.DEFAULT;
    public Optional<HotbarHudBarRenderState> experienceBar = Optional.empty();

    public HotbarHudCursorRenderState cursorRenderState = HotbarHudCursorRenderState.DEFAULT;

    public PlayerInventory playerInventory = null;
    public ItemStack offHandStack;


    // Rendering Methods
    public void render(DrawContext context, TextRenderer textRenderer, float tickProgress) {
        if (this.shouldRender()) {
            this.renderBackground(context, textRenderer, this.x, this.y, tickProgress);
            this.renderBars(context, textRenderer, this.x, this.y, tickProgress);
            this.renderItems(context, textRenderer, this.x, this.y, tickProgress);
        }
        if (ModConfig.hotbarHudCursor) {
            this.cursorRenderState.render(context, textRenderer, tickProgress);
        }
    }

    public void renderBackground(DrawContext context, TextRenderer textRenderer, int x, int y, float tickProgress) {
        int backgroundColor = adjustColor(ColorHelper.getArgb(
                (int) (ModConfig.hotbarHudBackgroundOpacity * 255),
                ModConfig.hotbarHudBackgroundColorRed,
                ModConfig.hotbarHudBackgroundColorGreen,
                ModConfig.hotbarHudBackgroundColorBlue
        ));

        // Stats & Held Item
        UltraEffectsUtil.renderRoundBox(context, x, y - 4, x + 127, y - 26, backgroundColor);

        if (this.experienceBar.isPresent()) {
            UltraEffectsUtil.renderRoundBox(context, x, y - 96, x + 127, y - 109, backgroundColor);
        }

        // Held Item
        UltraEffectsUtil.renderRoundBox(context, x, y - 27, x + 127, y - 95, backgroundColor);

        // Hotbar
        UltraEffectsUtil.renderRoundBox(context, x + 128, y - 27, x + 146, y - 95, backgroundColor);

        int selectedColor = this.adjustColor(ColorHelper.withBrightness(backgroundColor, 0.65F));
        int outerColor = this.adjustColor(ColorHelper.withBrightness(backgroundColor, 0.35F));

        context.fill(x + 129, y - 53, x + 145, y - 54, selectedColor);
        context.fill(x + 129, y - 70, x + 145, y - 71, selectedColor);

        context.fill(x + 129, y - 38, x + 145, y - 39, outerColor);
        context.fill(x + 129, y - 85, x + 145, y - 86, outerColor);

        // Offhand
        UltraEffectsUtil.renderRoundBox(context, x + 128, y - 4, x + 146, y - 26, backgroundColor);
    }

    public void renderBars(DrawContext context, TextRenderer textRenderer, int x, int y, float tickProgress) {
        this.healthBar.render(this, context, x + 4, y - 20, tickProgress, true);
        this.absorptionBar.render(this, context, x + 4, y - 20, tickProgress, false);
        this.staminaBar.render(this, context, x + 4, y - 10, tickProgress, true);
        this.experienceBar.ifPresent(experienceBar -> experienceBar.render(this, context, x + 4, y - 103, tickProgress, true));

        if (ModConfig.hotbarHudHealthNumberDisplay != ModConfig.HotbarHudHealthNumberDisplay.NEVER) {
            MutableText healthText = Text.literal("+" + ModConfig.hotbarHudHealthNumberDisplay.healthGetter.apply(this.healthBar, this.absorptionBar, tickProgress));
            context.drawText(textRenderer, healthText, x + 6, y - 24, adjustColor(Colors.WHITE), false);
        }
    }

    public void renderItems(DrawContext context, TextRenderer textRenderer, int x, int y, float tickProgress) {
        if (this.getVisibility() < 1) {
            UltraEffectsUtil.setAlphaOverride(context, this.getVisibility());
        }
        if (this.playerInventory != null) {

            // Main Hand
            ItemStack mainHandStack = this.playerInventory.getSelectedStack();

            context.getMatrices().pushMatrix();
            context.getMatrices().translate(x + 31, y - 92.5F);

            context.getMatrices().scale(4, 4);
            context.drawItem(mainHandStack, 0, 0);

            context.getMatrices().popMatrix();

            // Hotbar
            for (int i = -2; i <= 2; i++) {
                context.getMatrices().pushMatrix();
                context.getMatrices().translate(x + 129, y - 70 + (16 * MathHelper.clamp(i, -1.8F, 1.8F)));
                if (i != 0) {
                    context.getMatrices().translate(2 * Math.abs(i), 2 * Math.abs(i));
                    context.getMatrices().scale(1 - Math.abs(0.25F * i), 1 - Math.abs(0.25F * i));
                }

                context.drawItem(this.getInventorySlot(this.playerInventory, i), 0, 0);
                context.drawStackOverlay(textRenderer, this.getInventorySlot(playerInventory, i), 0, 0);

                context.getMatrices().popMatrix();
            }

            // Offhand
            context.getMatrices().pushMatrix();
            context.drawItem(this.offHandStack, x + 129, y - 23);
            context.drawStackOverlay(textRenderer, this.offHandStack, x + 129, y - 23);
            context.getMatrices().popMatrix();

            // Main Hand Stack Name Display
            if (!mainHandStack.isEmpty()) {
                MutableText mutableText = Text.empty().append(mainHandStack.getName()).formatted(mainHandStack.getRarity().getFormatting());
                if (mainHandStack.contains(DataComponentTypes.CUSTOM_NAME)) {
                    mutableText.formatted(Formatting.ITALIC);
                }
                int textWidth = textRenderer.getWidth(mutableText);

                int displayAlpha = switch (ModConfig.hotbarHudItemNameDisplay) {
                    case ALWAYS -> 255;
                    case NEVER -> 0;
                    case null, default ->
                            Math.min(255, (int) ((float) this.heldItemTooltipFade * 256 / 10F));
                };

                if (displayAlpha > 0) {
                    context.getMatrices().pushMatrix();
                    context.getMatrices().translate(x + 64, y - 92);
                    if (textWidth > 120) {
                        context.getMatrices().scale(Math.max(0.25F, 1 - ((textWidth - 120) * 0.0065F)));
                    }

                    context.drawCenteredTextWithShadow(textRenderer, mutableText, 0, 0, ColorHelper.withAlpha(displayAlpha, -1));
                    context.getMatrices().popMatrix();
                }
            }
        }
        UltraEffectsUtil.resetAlphaOverride(context);
    }


    // Helper Methods
    public ItemStack getInventorySlot(PlayerInventory inventory, int offset) {
        int slot = inventory.getSelectedSlot() + offset;
        if (slot < 0) {
            slot = 9 + slot;
        } else if (slot > 8) {
            slot = slot - 9;
        }

        return inventory.getMainStacks().get(slot);
    }

    public int adjustColor(int color) {
        return ColorHelper.withAlpha((int) (this.getVisibility() * ColorHelper.getAlpha(color)), color);
    }

    public float getVisibility() {
        if (this.chatFocused) {
            return switch (ModConfig.hotbarHudChatFocusModification) {
                case SHIFTED_CHAT -> 1F;
                case TRANSPARENT_HUD -> 0.25F;
                case HIDDEN_HUD -> 0F;
            };
        }
        return 1F;
    }

    public boolean shouldRender() {
        return ModConfig.hotbarHudEnabled && this.getVisibility() > 0 && !UltraEffectsUtil.isRunningDeathScreenOverhaul();
    }

    public boolean shouldShiftChat(MinecraftClient client) {
        return this.shouldRender() && !UltraEffectsUtil.isSpectator() && client.options.getMainArm().getValue() == Arm.RIGHT;
    }

}
