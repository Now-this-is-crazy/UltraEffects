package powercyphe.ultraeffects.util;

import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class ComboHelper {

    public static DefaultedList<ComboInstance> COMBO_TYPES = DefaultedList.of();

    public static ComboInstance MELEE = register(new ComboInstance("melee", 30, 3, true, combo -> {
        return 10 + Math.clamp((combo-2) * 15F, 0F, 100F);
    }));
    public static ComboInstance PROJECTILE = register(new ComboInstance("projectile", 0, 1, false, combo -> {
        return 10 + Math.clamp((combo-2) * 20F, 0F, 100F);
    }));
    public static ComboInstance KILL = register(new ComboInstance("kill", 1, 2, false, combo -> {
        return 10 + Math.clamp((combo-3) * 30F, 0F, 100F);
    }));

    public static ComboInstance register(ComboInstance comboInstance) {
        COMBO_TYPES.add(comboInstance);
        return comboInstance;
    }

    public static void tick() {
        for (ComboInstance comboInstance : COMBO_TYPES) {
            comboInstance.tick();
        }
    }

    public static class ComboInstance {
        private int combo = 0;
        private int ticks = 0;
        private Entity entity;

        private final String id;
        private final int defaultTicks;
        private final int required;
        private final boolean entityBound;

        private final Function<Integer, Float> pointCalculation;

        public ComboInstance(String id, int defaultTicks, int required, boolean entityBound, Function<Integer, Float> pointCalculation) {
            this.id = id;
            this.defaultTicks = defaultTicks;
            this.required = required;
            this.entityBound = entityBound;

            this.pointCalculation = pointCalculation;
        }

        public void tick() {
            if (this.ticks > 0) {
                this.ticks--;
                if (this.entityBound && this.entity != null && !this.entity.isAlive()) {
                    this.ticks = 0;
                }
            } else if (this.combo > 0) {
                reset();
            }
        }

        public boolean set(int combo) {
            this.combo = combo;
            this.ticks = this.defaultTicks;

            if (this.combo >= this.required) {
                UltraEffectsUtil.addStyle(Text.translatable("ultraeffects.style_meter.style.combo_" + this.id, this.combo), this.pointCalculation.apply(this.combo));
                return true;
            }
            return false;
        }

        public boolean increase(@Nullable Entity entity) {
            if (entity != null && this.entityBound && this.entity != entity) {
                this.combo = 0;
                this.entity = entity;
            }
            this.combo++;
            this.ticks = this.defaultTicks;

            if (this.combo >= this.required) {
                UltraEffectsUtil.addStyle(Text.translatable("ultraeffects.style_meter.style.combo_" + this.id, this.combo), this.pointCalculation.apply(this.combo));
                return true;
            }
            return false;
        }

        public void reset() {
            this.combo = 0;
            this.ticks = 0;
            this.entity = null;
        }
    }
}
