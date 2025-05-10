package powercyphe.ultraeffects.effect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import powercyphe.ultraeffects.ModConfig;
import powercyphe.ultraeffects.registry.ModSounds;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StyleMeterEffect extends TickingEffect {

    private static final List<Pair<String, HashMap<String, Object>>> STYLE_LIST = List.of(
        new Pair<>("none", rank(0, 0xff0000, 10F)),
        new Pair<>("destructive", rank(200, 0x0193fb, 15F)),
        new Pair<>("chaotic", rank(300, 0x4cff00, 18.75F)),
        new Pair<>("brutal", rank(400, 0xfed800, 22.5F)),
        new Pair<>("anarchic", rank(500, 0xff6a00, 30F)),
        new Pair<>("supreme", rank(700, 0xff0000, 45F)),
        new Pair<>("ssadistic", rank(850, 0xff0000, 60F)),
        new Pair<>("ssshitstorm", rank(1000,0xff0000, 90F)),
        new Pair<>("ultrakill", rank(1500, 0xffd700, 120F))
    );

    public String styleRank = STYLE_LIST.getFirst().getLeft();
    public int styleColor = 0xFFFFFF;

    public float style, drainPerSecond = 0;

    public final int defaultThreshold = getThreshold(STYLE_LIST.get(1).getRight());
    public float threshold, nextThreshold = 0;

    public DefaultedList<Pair<Text, Integer>> styleList = DefaultedList.of();
    public DefaultedList<Pair<Text, Float>> styleQueue = DefaultedList.of();

    @Override
    public void display() {}

    @Override
    public void tick() {
        if (this.style > 0) {
            this.style = Math.clamp(this.style - (this.drainPerSecond / 20F), 0F, 3000F);

            if (this.style < this.threshold) {
                updateStyleRank();
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
                if (ModConfig.styleMeterSound) {
                    UltraEffectsUtil.playSound(ModSounds.STYLE_METER_CLICK, SoundCategory.PLAYERS, 0.25F, 1F);
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
        List<Pair<String, HashMap<String, Object>>> ranks = List.copyOf(STYLE_LIST);
        int currentThreshold = -1;

        for (Pair<String, HashMap<String, Object>> pair : ranks) {
            String styleRank = pair.getLeft();
            HashMap<String, Object> map = pair.getRight();
            int index = ranks.indexOf(pair);

            int color = getColor(map);
            int threshold = getThreshold(map);
            float drain = getDrainPerSecond(map);

            if (this.style >= threshold && threshold > currentThreshold) {
                this.styleRank = styleRank;
                this.styleColor = color;

                this.threshold = threshold;
                if (index+1 < ranks.size()) {
                    this.nextThreshold = getThreshold(ranks.get(index+1).getRight());
                } else if (this.nextThreshold < this.style) {
                    this.nextThreshold = this.style;
                }

                this.drainPerSecond = drain;

                currentThreshold = threshold;
            }
        }

    }

    public boolean shouldDisplay() {
        if (MinecraftClient.getInstance().inGameHud.getDebugHud().shouldShowDebugHud()) {
            return false;
        }
        return switch (ModConfig.styleMeterDisplayCondition) {
            case ALWAYS -> true;
            case ANY_STYLE -> this.style >= this.defaultThreshold || !this.styleList.isEmpty();
            case POINTS_ONLY -> this.style >= this.defaultThreshold;
            case EVENTS_ONLY -> !this.styleList.isEmpty();
            case null, default -> false;
        };
    }

    public String getPrefix() {
        return Text.translatable(("ultraeffects.style_meter." + this.styleRank + ".prefix")).getString();
    }

    public String getSuffix() {
        return Text.translatable(("ultraeffects.style_meter." + this.styleRank + ".suffix")).getString();
    }

    public float getLineMultiplier() {
        if (this.style < this.defaultThreshold) {
            return 0F;
        }
        return Math.clamp((this.style - this.threshold) / (this.nextThreshold - this.threshold), 0F, 1F);
    }

    private static int getThreshold(HashMap<String, Object> map) {
        return (int) map.get("threshold");
    }

    private static int getColor(HashMap<String, Object> map) {
        return (int) map.get("color");
    }

    private static float getDrainPerSecond(HashMap<String, Object> map) {
        return (float) map.get("drain");
    }

    private static HashMap<String, Object> rank(int threshold, int color, float drainPerSecond) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("threshold", threshold);
        map.put("color", color);
        map.put("drain", drainPerSecond);
        return map;
    }
}
