package powercyphe.ultraeffects;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import powercyphe.ultraeffects.effect.TickingEffect;
import powercyphe.ultraeffects.event.UEHudRenderer;
import powercyphe.ultraeffects.hud.old.HotbarHud;
import powercyphe.ultraeffects.registry.EffectRegistry;
import powercyphe.ultraeffects.registry.UEHuds;
import powercyphe.ultraeffects.util.ComboHelper;
import powercyphe.ultraeffects.util.UEUtil;

public class UltraEffectsClient implements ClientModInitializer {
	public static final String MOD_ID = "ultraeffects";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final HotbarHud HOTBAR_HUD = new HotbarHud();

	public static boolean HAS_APPLEKSKIN = false;
	public static boolean PARRY_DISABLED = false;

	@Override
	public void onInitializeClient() {
		MidnightConfig.init(MOD_ID, UEConfig.class);

        UEHuds.init();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			ComboHelper.tick();

			if (!client.isPaused() && UEUtil.getLocalPlayer() != null) {
				for (TickingEffect effect : EffectRegistry.getEffectsByType(TickingEffect.class)) {
					effect.tick();
				}
			}

			if (PARRY_DISABLED) {
				PARRY_DISABLED = false;
			}
		});

		HudElementRegistry.addLast(id("hud"), new UEHudRenderer());
		ClientTickEvents.START_CLIENT_TICK.register(HOTBAR_HUD);

		HAS_APPLEKSKIN = FabricLoader.getInstance().isModLoaded("appleskin");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

}