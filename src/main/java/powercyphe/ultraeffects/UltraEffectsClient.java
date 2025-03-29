package powercyphe.ultraeffects;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UltraEffectsClient implements ClientModInitializer {
	public static final String MOD_ID = "ultraeffects";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static SoundEvent PARRY = soundEvent("parry");
	public static SoundEvent GABRIEL = soundEvent("gabriel");

	@Override
	public void onInitializeClient() {
		MidnightConfig.init(MOD_ID, ModConfig.class);
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	public static SoundEvent soundEvent(String path) {
		return SoundEvent.of(UltraEffectsClient.id(path));
	}

}