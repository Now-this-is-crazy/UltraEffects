package powercyphe.ultraeffects.effect;

import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import powercyphe.ultraeffects.UltraEffectsClient;

import java.util.HashMap;
import java.util.List;

public class EffectRegistry {

    protected static HashMap<Identifier, ScreenEffect> EFFECTS = new HashMap<>();

    public static final FlashEffect FLASH_EFFECT = register("flash", new FlashEffect());
    public static final FreezeEffect FREEZE_EFFECT = register("freeze", new FreezeEffect());
    public static final GabrielEffect GABRIEL_EFFECT = register("gabriel", new GabrielEffect());

    public static <T extends ScreenEffect> T register(String id, T effect) {
        return register(UltraEffectsClient.id(id), effect);
    }

    public static <T extends ScreenEffect> T register(Identifier id, T effect) {
        EFFECTS.put(id, effect);
        return effect;
    }

    public static <T extends C, C extends ScreenEffect> List<T> getEffectsByType(Class<C> type) {
        DefaultedList<T> effectList = DefaultedList.of();
        for (ScreenEffect screenEffect : EFFECTS.values()) {
            if (type.isInstance(screenEffect)) {
                effectList.add((T) screenEffect);
            }
        }
        return effectList;
    }
}
