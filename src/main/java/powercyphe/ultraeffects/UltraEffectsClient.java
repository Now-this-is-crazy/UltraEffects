package powercyphe.ultraeffects;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import powercyphe.ultraeffects.effect.TickingEffect;
import powercyphe.ultraeffects.hud.HotbarHud;
import powercyphe.ultraeffects.hud.OverlayEffectHud;
import powercyphe.ultraeffects.hud.StyleMeterHud;
import powercyphe.ultraeffects.registry.EffectRegistry;
import powercyphe.ultraeffects.util.ComboHelper;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public class UltraEffectsClient implements ClientModInitializer {
	public static final String MOD_ID = "ultraeffects";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final HotbarHud HOTBAR_HUD = new HotbarHud();

	public static boolean HAS_APPLEKSKIN = false;
	public static boolean PARRY_DISABLED = false;

	@Override
	public void onInitializeClient() {
		MidnightConfig.init(MOD_ID, ModConfig.class);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			ComboHelper.tick();

			if (!client.isPaused() && UltraEffectsUtil.getClientPlayer() != null) {
				for (TickingEffect effect : EffectRegistry.getEffectsByType(TickingEffect.class)) {
					effect.tick();
				}
			}

			if (PARRY_DISABLED) {
				PARRY_DISABLED = false;
			}
		});

		HudElementRegistry.attachElementAfter(VanillaHudElements.MISC_OVERLAYS, id("overlay_effect"), new OverlayEffectHud());
		HudElementRegistry.attachElementAfter(VanillaHudElements.SCOREBOARD, id("style_meter"), new StyleMeterHud());

		HudElementRegistry.attachElementAfter(VanillaHudElements.HOTBAR, id("hotbar"), HOTBAR_HUD);
		ClientTickEvents.START_CLIENT_TICK.register(HOTBAR_HUD);

		HAS_APPLEKSKIN = FabricLoader.getInstance().isModLoaded("appleskin");
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

}