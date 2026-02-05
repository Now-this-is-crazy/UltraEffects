package powercyphe.ultraeffects.hud.state;

import org.jetbrains.annotations.Nullable;
import powercyphe.ultraeffects.hud.UEHudState;

import java.util.ArrayList;
import java.util.List;

public class StyleMeterHudState extends UEHudState {
    public float style = 0F;
    public float styleMultiplier = 0F;

    public List<StyleInfo> infos = new ArrayList<>();
    public @Nullable StyleRank rank = null;

    public static class StyleInfo {
        public final String key;
        public int duration;

        public StyleInfo(String key, int duration) {
            this.key = key;
            this.duration = duration;
        }

        public String translationKey() {
            return "ultraeffects.style_meter.info." + this.key;
        }
    }

    public enum StyleRank {
        DESTRUCTIVE(0x0193fb, 200F, 15F),
        CHAOTIC(0x4cff00, 300F, 18.75F),
        BRUTAL(0xfed800, 400F, 22.5F),
        ANARCHIC(0xff6a00, 500, 30F),
        SUPREME(0xff0000, 700, 45F),
        SSADISTIC(0xff0000, 850F, 60F),
        SSSHITSTORM( 0xff0000, 1000, 90F),
        ULTRAKILL(0xffd700, 1500F, 120F)
        ;

        private final String id;
        private final int color;

        private final float threshold;
        private final float drainPerSecond;

        StyleRank(int color, float threshold, float drainPerSecond) {
            this.id = this.name().toLowerCase();
            this.color = color;

            this.threshold = threshold;
            this.drainPerSecond = drainPerSecond;
        }

        public String id() {
            return this.id;
        }

        public int index() {
            return this.ordinal();
        }

        public int color() {
            return this.color;
        }

        public float threshold() {
            return this.threshold;
        }

        public float drainPerSecond() {
            return this.drainPerSecond;
        }

        public String translationKey(boolean suffix) {
            return "ultraeffects.style_meter.rank." + this.id() + (suffix ? ".suffix" : ".prefix");
        }

        @Nullable
        public static StyleRank fromIndex(int index) {
            for (StyleRank rank : StyleRank.values()) {
                if (index == rank.index()) {
                    return rank;
                }
            }

            return null;
        }
    }
}
