package powercyphe.ultraeffects.registry;

import net.minecraft.sound.SoundEvent;
import powercyphe.ultraeffects.UltraEffectsClient;

public class ModSounds {

    public static SoundEvent PARRY = soundEvent("parry");
    public static SoundEvent HAMMER_IMPACT = soundEvent("hammer_impact");
    public static SoundEvent GABRIEL = soundEvent("gabriel");

    public static SoundEvent DEATH_SEQUENCE = soundEvent("death_sequence");
    public static SoundEvent DEATH_SEQUENCE_END = soundEvent("death_sequence_end");
    public static SoundEvent DEATH_SKULL = soundEvent("death_skull");

    public static SoundEvent STYLE_METER_CLICK = soundEvent("style_meter_click");

    public static SoundEvent soundEvent(String path) {
        return SoundEvent.of(UltraEffectsClient.id(path));
    }
}
