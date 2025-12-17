package powercyphe.ultraeffects.hud.state;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.ARGB;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
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

    public Inventory playerInventory = null;
    public ItemStack offHandStack;


    // Rendering Methods
    public void render(GuiGraphics context, Font textRenderer, float tickProgress) {
        if (this.shouldRender()) {
            this.renderBackground(context, textRenderer, this.x, this.y, tickProgress);
            this.renderBars(context, textRenderer, this.x, this.y, tickProgress);
            this.renderItems(context, textRenderer, this.x, this.y, tickProgress);
        }
        if (ModConfig.hotbarHudCursor) {
            this.cursorRenderState.render(context, textRenderer, tickProgress);
        }
    }

    public void renderBackground(GuiGraphics context, Font textRenderer, int x, int y, float tickProgress) {
        int backgroundColor = adjustColor(ARGB.color(
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

        int selectedColor = this.adjustColor(ARGB.setBrightness(backgroundColor, 0.65F));
        int outerColor = this.adjustColor(ARGB.setBrightness(backgroundColor, 0.35F));

        context.fill(x + 129, y - 53, x + 145, y - 54, selectedColor);
        context.fill(x + 129, y - 70, x + 145, y - 71, selectedColor);

        context.fill(x + 129, y - 38, x + 145, y - 39, outerColor);
        context.fill(x + 129, y - 85, x + 145, y - 86, outerColor);

        // Offhand
        UltraEffectsUtil.renderRoundBox(context, x + 128, y - 4, x + 146, y - 26, backgroundColor);
    }

    public void renderBars(GuiGraphics context, Font textRenderer, int x, int y, float tickProgress) {
        this.healthBar.render(this, context, x + 4, y - 20, tickProgress, true);
        this.absorptionBar.render(this, context, x + 4, y - 20, tickProgress, false);
        this.staminaBar.render(this, context, x + 4, y - 10, tickProgress, true);
        this.experienceBar.ifPresent(experienceBar -> experienceBar.render(this, context, x + 4, y - 103, tickProgress, true));

        if (ModConfig.hotbarHudHealthNumberDisplay != ModConfig.HotbarHudHealthNumberDisplay.NEVER) {
            MutableComponent healthText = Component.literal("+" + ModConfig.hotbarHudHealthNumberDisplay.healthGetter.apply(this.healthBar, this.absorptionBar, tickProgress));
            context.drawString(textRenderer, healthText, x + 6, y - 24, adjustColor(CommonColors.WHITE), false);
        }
    }

    public void renderItems(GuiGraphics context, Font textRenderer, int x, int y, float tickProgress) {
        if (this.getVisibility() < 1) {
            UltraEffectsUtil.setAlphaOverride(context, this.getVisibility());
        }
        if (this.playerInventory != null) {

            // Main Hand
            ItemStack mainHandStack = this.playerInventory.getSelectedItem();

            context.pose().pushMatrix();
            context.pose().translate(x + 31, y - 92.5F);

            context.pose().scale(4, 4);
            context.renderItem(mainHandStack, 0, 0);

            context.pose().popMatrix();

            // Hotbar
            for (int i = -2; i <= 2; i++) {
                context.pose().pushMatrix();
                context.pose().translate(x + 129, y - 70 + (16 * Mth.clamp(i, -1.8F, 1.8F)));
                if (i != 0) {
                    context.pose().translate(2 * Math.abs(i), 2 * Math.abs(i));
                    context.pose().scale(1 - Math.abs(0.25F * i), 1 - Math.abs(0.25F * i));
                }

                context.renderItem(this.getInventorySlot(this.playerInventory, i), 0, 0);
                context.renderItemDecorations(textRenderer, this.getInventorySlot(playerInventory, i), 0, 0);

                context.pose().popMatrix();
            }

            // Offhand
            context.pose().pushMatrix();
            context.renderItem(this.offHandStack, x + 129, y - 23);
            context.renderItemDecorations(textRenderer, this.offHandStack, x + 129, y - 23);
            context.pose().popMatrix();

            // Main Hand Stack Name Display
            if (!mainHandStack.isEmpty()) {
                MutableComponent mutableText = Component.empty().append(mainHandStack.getHoverName()).withStyle(mainHandStack.getRarity().color());
                if (mainHandStack.has(DataComponents.CUSTOM_NAME)) {
                    mutableText.withStyle(ChatFormatting.ITALIC);
                }
                int textWidth = textRenderer.width(mutableText);

                int displayAlpha = switch (ModConfig.hotbarHudItemNameDisplay) {
                    case ALWAYS -> 255;
                    case NEVER -> 0;
                    case null, default ->
                            Math.min(255, (int) ((float) this.heldItemTooltipFade * 256 / 10F));
                };

                if (displayAlpha > 0) {
                    context.pose().pushMatrix();
                    context.pose().translate(x + 64, y - 92);
                    if (textWidth > 120) {
                        context.pose().scale(Math.max(0.25F, 1 - ((textWidth - 120) * 0.0065F)));
                    }

                    context.drawCenteredString(textRenderer, mutableText, 0, 0, ARGB.color(displayAlpha, -1));
                    context.pose().popMatrix();
                }
            }
        }
        UltraEffectsUtil.resetAlphaOverride(context);
    }


    // Helper Methods
    public ItemStack getInventorySlot(Inventory inventory, int offset) {
        int slot = inventory.getSelectedSlot() + offset;
        if (slot < 0) {
            slot = 9 + slot;
        } else if (slot > 8) {
            slot = slot - 9;
        }

        return inventory.getNonEquipmentItems().get(slot);
    }

    public int adjustColor(int color) {
        return ARGB.color((int) (this.getVisibility() * ARGB.alpha(color)), color);
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

    public boolean shouldShiftChat(Minecraft client) {
        return this.shouldRender() && !UltraEffectsUtil.isSpectator() && client.options.mainHand().get() == HumanoidArm.RIGHT;
    }

}
