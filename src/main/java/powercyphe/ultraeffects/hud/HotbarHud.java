package powercyphe.ultraeffects.hud;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.UltraEffectsClient;
import powercyphe.ultraeffects.hud.state.HotbarHudBarRenderState;
import powercyphe.ultraeffects.hud.state.HotbarHudCursorRenderState;
import powercyphe.ultraeffects.hud.state.HotbarHudRenderState;
import powercyphe.ultraeffects.mixin.accessor.InGameHudAccessor;
import powercyphe.ultraeffects.util.UltraEffectsUtil;
import squeek.appleskin.client.HUDOverlayHandler;
import squeek.appleskin.helpers.ConsumableFood;
import squeek.appleskin.helpers.FoodHelper;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class HotbarHud implements HudElement, ClientTickEvents.StartTick {
    private Font textRenderer = null;

    private final HotbarHudRenderState hotbarHudState = new HotbarHudRenderState();

    @Override
    public void render(GuiGraphics context, DeltaTracker renderTickCounter) {
        if (this.textRenderer != null) {
            this.hotbarHudState.render(context, this.textRenderer, renderTickCounter.getGameTimeDeltaPartialTick(true));
        }
    }

    @Override
    public void onStartTick(Minecraft client) {
        LocalPlayer clientPlayer = UltraEffectsUtil.getLocalPlayer();
        if (this.textRenderer == null) {
            this.textRenderer = client.font;
        }

        if (clientPlayer != null) {
            for (BarType barType : BarType.values()) {
                barType.tick(clientPlayer);
            }
        }

        this.updateRenderState(client);
    }

    public int getX(Minecraft client) {
        return switch (ModConfig.hotbarHudSide) {
            case LEFT -> 4;
            case RIGHT -> client.getWindow().getGuiScaledWidth() - 154;
            case null, default -> client.options.mainHand().get() == HumanoidArm.RIGHT ? 4 : client.getWindow().getGuiScaledWidth() - 154;
        };
    }

    public static HotbarHudRenderState getRenderState() {
        return UltraEffectsClient.HOTBAR_HUD.hotbarHudState;
    }

    public void updateRenderState(Minecraft client) {
        LocalPlayer clientPlayer = UltraEffectsUtil.getLocalPlayer();
        if (ModConfig.hotbarHudEnabled || ModConfig.hotbarHudCursor) {
            float extraHealth = 0F;
            float extraHunger = 0F;

            // Appleskin Compat
            if (UltraEffectsClient.HAS_APPLEKSKIN && clientPlayer != null) {
                FoodHelper.QueriedFoodResult result = HUDOverlayHandler.INSTANCE.heldFood.result(this.hotbarHudState.overlayTick, clientPlayer);
                if (result != null) {
                    extraHealth = FoodHelper.getEstimatedHealthIncrement(clientPlayer, new ConsumableFood(result.modifiedFoodComponent, result.consumableComponent));
                    extraHunger = result.modifiedFoodComponent.nutrition();
                }
            }

            // Hunger, Absorption & Hunger Bar
            this.hotbarHudState.healthBar = new HotbarHudBarRenderState(BarType.HEALTH, UltraEffectsClient.HAS_APPLEKSKIN, this.hotbarHudState.overlayTick, extraHealth, 118, 8, 0, 0);
            this.hotbarHudState.absorptionBar = new HotbarHudBarRenderState(BarType.ABSORPTION, (int) (118 * this.hotbarHudState.healthBar.getProgress(1)), 8, 0, 0);
            this.hotbarHudState.staminaBar = new HotbarHudBarRenderState(BarType.HUNGER, UltraEffectsClient.HAS_APPLEKSKIN, this.hotbarHudState.overlayTick, extraHunger, 118, 8, 2, 2);
        }

        // Hotbar Hud
        if (ModConfig.hotbarHudEnabled) {
            this.hotbarHudState.x = this.getX(client);
            this.hotbarHudState.y = client.getWindow().getGuiScaledHeight();

            this.hotbarHudState.overlayTick += 1;
            if (this.hotbarHudState.overlayTick >= 50) {
                this.hotbarHudState.overlayTick = 0;
            }
            this.hotbarHudState.heldItemTooltipFade = ((InGameHudAccessor) client.gui).ultraeffects$getHeldItemToolTipFade();
            this.hotbarHudState.chatFocused = client.gui.getChat().isChatFocused();

            if (clientPlayer != null) {
                this.hotbarHudState.playerInventory = clientPlayer.getInventory();
                this.hotbarHudState.offHandStack = clientPlayer.getOffhandItem();

                // Experience Bar
                this.hotbarHudState.experienceBar = clientPlayer.isCreative() ? Optional.empty() : Optional.of(new HotbarHudBarRenderState(BarType.EXPERIENCE, 118, 9, 15, 1));
            }
        }

        // Cursor Hud
        if (ModConfig.hotbarHudCursor) {
            float healthFade = this.hotbarHudState.cursorRenderState.healthFade();
            if (this.hotbarHudState.healthBar.smoothedPrevious() != this.hotbarHudState.healthBar.smoothedCurrent()
                    || this.hotbarHudState.absorptionBar.smoothedPrevious() != this.hotbarHudState.absorptionBar.smoothedCurrent()) {
                healthFade = 20;
            } else {
                healthFade *= 0.57F;
            }

            float staminaFade = this.hotbarHudState.cursorRenderState.staminaFade();
            if (this.hotbarHudState.staminaBar.smoothedPrevious() != this.hotbarHudState.staminaBar.smoothedCurrent()) {
                staminaFade = 20;
            } else {
                staminaFade *= 0.57F;
            }

            this.hotbarHudState.cursorRenderState = new HotbarHudCursorRenderState(this.hotbarHudState.healthBar, this.hotbarHudState.absorptionBar, healthFade, this.hotbarHudState.staminaBar, staminaFade);
        }
    }


    // Status Bars
    public enum BarType {
        HEALTH(LivingEntity::getHealth, LivingEntity::getMaxHealth, 0xfe0000, 0xff1313,
                () -> ARGB.color(ModConfig.hotbarHudHealthColorRed, ModConfig.hotbarHudHealthColorGreen, ModConfig.hotbarHudHealthColorBlue)),
        ABSORPTION(LivingEntity::getAbsorptionAmount, LivingEntity::getMaxAbsorption, 0x00ff01, 0xd4af37,
                () -> ARGB.color(ModConfig.hotbarHudAbsorptionColorRed, ModConfig.hotbarHudAbsorptionColorGreen, ModConfig.hotbarHudAbsorptionColorBlue)),

        /*
        ARMOR(clientPlayer -> (float) clientPlayer.getArmor(), clientPlayer -> 20F, 0xb8b9c4, 0xb8b9c4,
                () -> ColorHelper.getArgb(ModConfig.hotbarHudArmorColorRed, ModConfig.hotbarHudArmorColorGreen, ModConfig.hotbarHudArmorColorBlue)),
        REMAINING_BREATH(clientPlayer -> (float) clientPlayer.getMaxAir() - Math.max(0, clientPlayer.getAir()), clientPlayer -> (float) clientPlayer.getMaxAir(), 0x0094ff, 0x0094ff,
                () -> ColorHelper.getArgb(ModConfig.hotbarHudAirColorRed, ModConfig.hotbarHudAirColorGreen, ModConfig.hotbarHudAirColorBlue)),
        */

        HUNGER(clientPlayer -> (float) clientPlayer.getFoodData().getFoodLevel(), clientPlayer -> 20F, 0x00dafe, 0x9d6d43,
                () -> ARGB.color(ModConfig.hotbarHudHungerColorRed, ModConfig.hotbarHudHungerColorGreen, ModConfig.hotbarHudHungerColorBlue)),
        EXPERIENCE(clientPlayer -> clientPlayer.experienceProgress * clientPlayer.getXpNeededForNextLevel(), clientPlayer -> (float) clientPlayer.getXpNeededForNextLevel(), 0xb3f37d, 0xb3f37d,
                () -> ARGB.color(ModConfig.hotbarHudExperienceColorRed, ModConfig.hotbarHudExperienceColorGreen, ModConfig.hotbarHudExperienceColorBlue))
        ;

        private final Function<LocalPlayer, Float> currentGetter;
        private final Function<LocalPlayer, Float> maxGetter;

        private final int ultrakillColor;
        private final int vanillaColor;
        private final Supplier<Integer> customColor;

        public float current = 0F;
        public float max = 0F;

        public float smoothedCurrent = 0F;
        public float smoothedPrevious = 0F;

        BarType(Function<LocalPlayer, Float> currentGetter, Function<LocalPlayer, Float> maxGetter,
                int ultrakillColor, int vanillaColor, Supplier<Integer> customColor) {
            this.currentGetter = currentGetter;
            this.maxGetter = maxGetter;

            this.ultrakillColor = ultrakillColor;
            this.vanillaColor = vanillaColor;
            this.customColor = customColor;
        }

        public void tick(@NotNull LocalPlayer player) {
            this.smoothedPrevious = this.smoothedCurrent;
            this.smoothedCurrent = getSmoothNumber(this.current, this.smoothedCurrent);

            this.current = this.currentGetter.apply(player);
            this.max = this.maxGetter.apply(player) > 0 ? this.maxGetter.apply(player) : this.max;
        }

        public int getColor() {
            return switch (ModConfig.hotbarHudColors) {
                case VANILLA -> ARGB.opaque(this.vanillaColor);
                case CUSTOM -> ARGB.opaque(this.customColor.get());
                case null, default -> ARGB.opaque(this.ultrakillColor);
            };
        }

        public static float getSmoothNumber(float current, float smoothed) {
            if (Math.abs(current - smoothed) < 0.1) {
                return current;
            }
            return Math.max(smoothed + ( (current - smoothed) / 2), 0F);
        }
    }
}