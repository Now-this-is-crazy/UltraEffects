package powercyphe.ultraeffects;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import powercyphe.ultraeffects.effect.TickingEffect;
import powercyphe.ultraeffects.hud.OverlayEffectHud;
import powercyphe.ultraeffects.hud.StyleMeterHud;
import powercyphe.ultraeffects.registry.EffectRegistry;
import powercyphe.ultraeffects.util.ComboHelper;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public class UltraEffectsClient implements ClientModInitializer {
	public static final String MOD_ID = "ultraeffects";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

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

		HudLayerRegistrationCallback.EVENT.register(new OverlayEffectHud());
		HudLayerRegistrationCallback.EVENT.register(new StyleMeterHud());
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

}