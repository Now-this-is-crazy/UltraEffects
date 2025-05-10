package powercyphe.ultraeffects.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.collection.DefaultedList;

public interface HitEntities {

    void ultraeffects$setHitEntities(DefaultedList<Entity> entities);
    DefaultedList<Entity> ultraeffects$getHitEntities();
}
