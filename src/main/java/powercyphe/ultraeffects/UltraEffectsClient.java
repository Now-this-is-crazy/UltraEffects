package powercyphe.ultraeffects;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import powercyphe.ultraeffects.effect.EffectRegistry;
import powercyphe.ultraeffects.effect.TickingEffect;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public class UltraEffectsClient implements ClientModInitializer {
	public static final String MOD_ID = "ultraeffects";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static SoundEvent PARRY = soundEvent("parry");
	public static SoundEvent HAMMER_IMPACT = soundEvent("hammer_impact");
	public static SoundEvent GABRIEL = soundEvent("gabriel");

	@Override
	public void onInitializeClient() {
		MidnightConfig.init(MOD_ID, ModConfig.class);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (!client.isPaused() && UltraEffectsUtil.getClientPlayer() != null) {
				for (TickingEffect effect : EffectRegistry.getEffectsByType(TickingEffect.class)) {
					effect.tick();
				}
			}
		});
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	public static SoundEvent soundEvent(String path) {
		return SoundEvent.of(UltraEffectsClient.id(path));
	}

}