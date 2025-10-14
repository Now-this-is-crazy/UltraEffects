package powercyphe.ultraeffects.hud;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.math.ColorHelper;
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
    private TextRenderer textRenderer = null;

    private final HotbarHudRenderState hotbarHudState = new HotbarHudRenderState();

    @Override
    public void render(DrawContext context, RenderTickCounter renderTickCounter) {
        if (this.textRenderer != null) {
            this.hotbarHudState.render(context, this.textRenderer, renderTickCounter.getTickProgress(true));
        }
    }

    @Override
    public void onStartTick(MinecraftClient client) {
        ClientPlayerEntity clientPlayer = UltraEffectsUtil.getClientPlayer();
        if (this.textRenderer == null) {
            this.textRenderer = client.textRenderer;
        }

        if (clientPlayer != null) {
            for (BarType barType : BarType.values()) {
                barType.tick(clientPlayer);
            }
        }

        this.updateRenderState(client);
    }

    public int getX(MinecraftClient client) {
        return switch (ModConfig.hotbarHudSide) {
            case LEFT -> 4;
            case RIGHT -> client.getWindow().getScaledWidth() - 154;
            case null, default -> client.options.getMainArm().getValue() == Arm.RIGHT ? 4 : client.getWindow().getScaledWidth() - 154;
        };
    }

    public static HotbarHudRenderState getRenderState() {
        return UltraEffectsClient.HOTBAR_HUD.hotbarHudState;
    }

    public void updateRenderState(MinecraftClient client) {
        ClientPlayerEntity clientPlayer = UltraEffectsUtil.getClientPlayer();
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
            this.hotbarHudState.y = client.getWindow().getScaledHeight();

            this.hotbarHudState.overlayTick += 1;
            if (this.hotbarHudState.overlayTick >= 50) {
                this.hotbarHudState.overlayTick = 0;
            }
            this.hotbarHudState.heldItemTooltipFade = ((InGameHudAccessor) client.inGameHud).ultraeffects$getHeldItemToolTipFade();
            this.hotbarHudState.chatFocused = client.inGameHud.getChatHud().isChatFocused();

            if (clientPlayer != null) {
                this.hotbarHudState.playerInventory = clientPlayer.getInventory();
                this.hotbarHudState.offHandStack = clientPlayer.getOffHandStack();

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
                () -> ColorHelper.getArgb(ModConfig.hotbarHudHealthColorRed, ModConfig.hotbarHudHealthColorGreen, ModConfig.hotbarHudHealthColorBlue)),
        ABSORPTION(LivingEntity::getAbsorptionAmount, LivingEntity::getMaxAbsorption, 0x00ff01, 0xd4af37,
                () -> ColorHelper.getArgb(ModConfig.hotbarHudAbsorptionColorRed, ModConfig.hotbarHudAbsorptionColorGreen, ModConfig.hotbarHudAbsorptionColorBlue)),

        /*
        ARMOR(clientPlayer -> (float) clientPlayer.getArmor(), clientPlayer -> 20F, 0xb8b9c4, 0xb8b9c4,
                () -> ColorHelper.getArgb(ModConfig.hotbarHudArmorColorRed, ModConfig.hotbarHudArmorColorGreen, ModConfig.hotbarHudArmorColorBlue)),
        REMAINING_BREATH(clientPlayer -> (float) clientPlayer.getMaxAir() - Math.max(0, clientPlayer.getAir()), clientPlayer -> (float) clientPlayer.getMaxAir(), 0x0094ff, 0x0094ff,
                () -> ColorHelper.getArgb(ModConfig.hotbarHudAirColorRed, ModConfig.hotbarHudAirColorGreen, ModConfig.hotbarHudAirColorBlue)),
        */

        HUNGER(clientPlayer -> (float) clientPlayer.getHungerManager().getFoodLevel(), clientPlayer -> 20F, 0x00dafe, 0x9d6d43,
                () -> ColorHelper.getArgb(ModConfig.hotbarHudHungerColorRed, ModConfig.hotbarHudHungerColorGreen, ModConfig.hotbarHudHungerColorBlue)),
        EXPERIENCE(clientPlayer -> clientPlayer.experienceProgress * clientPlayer.getNextLevelExperience(), clientPlayer -> (float) clientPlayer.getNextLevelExperience(), 0xb3f37d, 0xb3f37d,
                () -> ColorHelper.getArgb(ModConfig.hotbarHudExperienceColorRed, ModConfig.hotbarHudExperienceColorGreen, ModConfig.hotbarHudExperienceColorBlue))
        ;

        private final Function<ClientPlayerEntity, Float> currentGetter;
        private final Function<ClientPlayerEntity, Float> maxGetter;

        private final int ultrakillColor;
        private final int vanillaColor;
        private final Supplier<Integer> customColor;

        public float current = 0F;
        public float max = 0F;

        public float smoothedCurrent = 0F;
        public float smoothedPrevious = 0F;

        BarType(Function<ClientPlayerEntity, Float> currentGetter, Function<ClientPlayerEntity, Float> maxGetter,
                int ultrakillColor, int vanillaColor, Supplier<Integer> customColor) {
            this.currentGetter = currentGetter;
            this.maxGetter = maxGetter;

            this.ultrakillColor = ultrakillColor;
            this.vanillaColor = vanillaColor;
            this.customColor = customColor;
        }

        public void tick(@NotNull ClientPlayerEntity player) {
            this.smoothedPrevious = this.smoothedCurrent;
            this.smoothedCurrent = getSmoothNumber(this.current, this.smoothedCurrent);

            this.current = this.currentGetter.apply(player);
            this.max = this.maxGetter.apply(player) > 0 ? this.maxGetter.apply(player) : this.max;
        }

        public int getColor() {
            return switch (ModConfig.hotbarHudColors) {
                case VANILLA -> ColorHelper.fullAlpha(this.vanillaColor);
                case CUSTOM -> ColorHelper.fullAlpha(this.customColor.get());
                case null, default -> ColorHelper.fullAlpha(this.ultrakillColor);
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