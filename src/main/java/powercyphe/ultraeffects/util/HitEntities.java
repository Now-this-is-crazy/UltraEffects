package powercyphe.ultraeffects.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;

public interface HitEntities {

    void ultraeffects$setHitEntities(NonNullList<Entity> entities);
    NonNullList<Entity> ultraeffects$getHitEntities();
}
