package powercyphe.ultraeffects.registry;

import powercyphe.ultraeffects.hud.UEHud;
import powercyphe.ultraeffects.hud.StyleMeterHud;

public class UEHuds {
    public static final StyleMeterHud STYLE_METER = register("style_meter", new StyleMeterHud());

    public static void init() {}

    public static <T extends UEHud<?>> T register(String id, T hud) {
        return UERegistry.register(UERegistries.HUD, id, hud);
    }
}
