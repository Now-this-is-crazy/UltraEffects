package powercyphe.ultraeffects.sound;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public class UnstoppableSoundInstance extends MovingSoundInstance {

    public UnstoppableSoundInstance(SoundEvent soundEvent, SoundCategory soundCategory, float pitch, float volume) {
        super(soundEvent, soundCategory, Random.create());
        this.repeat = false;

        this.pitch = pitch;
        this.volume = volume;
        this.tick();
    }

    @Override
    public void tick() {
        ClientPlayerEntity clientPlayer = UltraEffectsUtil.getClientPlayer();
        if (clientPlayer != null) {
            this.x = clientPlayer.getX();
            this.y = clientPlayer.getY();
            this.z = clientPlayer.getZ();
        }
    }

    @Override
    public boolean shouldAlwaysPlay() {
        return true;
    }
}
