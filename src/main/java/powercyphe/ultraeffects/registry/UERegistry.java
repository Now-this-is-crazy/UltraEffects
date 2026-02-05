package powercyphe.ultraeffects.registry;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import powercyphe.ultraeffects.UltraEffectsClient;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UERegistry<T> implements Iterable<T> {
    private final Map<Identifier, T> registered = new HashMap<>();

    public final Map<Identifier, T> getRegistered() {
        return this.registered;
    }

    public void register(Identifier id, T obj) {
        this.getRegistered().put(id, obj);
    }

    public static <V, T extends V> T register(UERegistry<V> registry, String id, T obj) {
        registry.register(UltraEffectsClient.id(id), obj);
        return obj;
    }

    public static <V, T extends V> T register(UERegistry<V> registry, Identifier id, T obj) {
        registry.register(id, obj);
        return obj;
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return this.registered.values().iterator();
    }
}
