package powercyphe.ultraeffects.effect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.registry.ModSounds;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

import java.util.ArrayList;
import java.util.List;

public class StyleMeterEffect extends TickingEffect {

    public StyleRank styleRank = StyleRank.DESTRUCTIVE;
    public float style, drainPerSecond = 0;

    public float threshold, nextThreshold = 0;
    public static float styleMax = getNextThreshold(StyleRank.values()[StyleRank.values().length-1]);

    public DefaultedList<Pair<Text, Integer>> styleList = DefaultedList.of();
    public DefaultedList<Pair<Text, Float>> styleQueue = DefaultedList.of();

    @Override
    public void display() {}

    @Override
    public void tick() {
        if (this.style > 0) {
            this.style = MathHelper.clamp(this.style - (this.drainPerSecond / 20F), 0F, styleMax);

            if (this.style < this.threshold) {
                updateStyleRank();
                this.style = this.threshold + ((this.nextThreshold - this.threshold) * 0.7F);
            }
        }

        if (!this.styleQueue.isEmpty()) {
            Pair<Text, Float> pair = this.styleQueue.removeFirst();
            this.style += pair.getRight();

            if (!pair.getLeft().getString().isEmpty()) {
                if (this.styleList.size() > 6) {
                    this.styleList.remove(6);
                }
                this.styleList.addFirst(new Pair<>(pair.getLeft(), 140));
                if (this.shouldDisplay() && ModConfig.styleMeterSound) {
                    UltraEffectsUtil.playSound(ModSounds.STYLE_METER_CLICK, SoundCategory.PLAYERS, 1F, 0.25F);
                }
            }
            updateStyleRank();
        }

        List<Pair<Text, Integer>> marked = new ArrayList<>();
        for (Pair<Text, Integer> pair : this.styleList) {
            int index = this.styleList.indexOf(pair);
            int styleDuration = pair.getRight();

            styleDuration--;
            if (styleDuration <= 0) {
                marked.add(pair);
            } else {
                pair.setRight(styleDuration);
                this.styleList.set(index, pair);
            }
        }

        this.styleList.removeAll(marked);
    }

    public void addStyle(Text text, float points) {
        this.styleQueue.add(new Pair<>(text, points));
    }

    public void updateStyleRank() {
        for (StyleRank rank : StyleRank.values()) {
            float requiredStyle = getThreshold(rank);

            if (this.style > requiredStyle) {
                this.styleRank = rank;

                this.threshold = getThreshold(rank);
                this.nextThreshold = getNextThreshold(rank);

                this.drainPerSecond = rank.getDrainPerSecond();
            }
        }

    }

    public boolean shouldDisplay() {
        if (MinecraftClient.getInstance().debugHudEntryList.isF3Enabled()) {
            return false;
        }
        return switch (ModConfig.styleMeterDisplayCondition) {
            case ALWAYS -> true;
            case ANY_STYLE -> this.style > 0 || !this.styleList.isEmpty();
            case POINTS_ONLY -> this.style > 0;
            case EVENTS_ONLY -> !this.styleList.isEmpty();
            case null, default -> false;
        };
    }

    public float getLineMultiplier() {
        if (this.style <= 0) {
            return 0F;
        }
        return MathHelper.clamp((this.style - this.threshold) / (this.nextThreshold - this.threshold), 0F, 1F);
    }

    private static float getThreshold(@NotNull StyleRank rank) {
        float t = 0;
        for (int index = 0; index < rank.getIndex(); index++) {
            StyleRank r = StyleRank.fromIndex(index);
            if (r != null) {
                t += r.getThreshold();
            }
        }
        return t;
    }

    private static float getNextThreshold(@NotNull StyleRank rank) {
        return getThreshold(rank) + rank.getThreshold();
    }

    public enum StyleRank {
        DESTRUCTIVE(0, 0x0193fb, 200F, 15F),
        CHAOTIC(1, 0x4cff00, 300F, 18.75F),
        BRUTAL(2, 0xfed800, 400F, 22.5F),
        ANARCHIC(3, 0xff6a00, 500, 30F),
        SUPREME(4, 0xff0000, 700, 45F),
        SSADISTIC(5, 0xff0000, 850F, 60F),
        SSSHITSTORM(6, 0xff0000, 1000, 90F),
        ULTRAKILL(7, 0xffd700, 1500F, 120F)
        ;

        private final String id;
        private final int index;
        private final int color;

        private final float threshold;
        private final float drainPerSecond;

        StyleRank(int index, int color, float threshold, float drainPerSecond) {
            this.id = this.name().toLowerCase();
            this.index = index;
            this.color = color;

            this.threshold = threshold;
            this.drainPerSecond = drainPerSecond;
        }

        public String getId() {
            return this.id;
        }

        public int getIndex() {
            return this.index;
        }

        public int getColor() {
            return this.color;
        }

        public float getThreshold() {
            return this.threshold;
        }

        public float getDrainPerSecond() {
            return this.drainPerSecond;
        }

        public String getTranslationKey(boolean suffix) {
            return "ultraeffects.style_meter." + this.getId() + (suffix ? ".suffix" : ".prefix");
        }

        @Nullable
        public static StyleRank fromIndex(int index) {
            for (StyleRank rank : StyleRank.values()) {
                if (index == rank.getIndex()) {
                    return rank;
                }
            }

            return null;
        }
    }
}
