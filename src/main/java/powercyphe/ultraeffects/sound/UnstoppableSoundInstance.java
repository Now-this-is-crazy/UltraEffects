package powercyphe.ultraeffects.sound;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import powercyphe.ultraeffects.util.UltraEffectsUtil;

public class UnstoppableSoundInstance extends AbstractTickableSoundInstance {

    public UnstoppableSoundInstance(SoundEvent soundEvent, SoundSource soundCategory, float pitch, float volume) {
        super(soundEvent, soundCategory, RandomSource.create());
        this.looping = false;

        this.pitch = pitch;
        this.volume = volume;
        this.tick();
    }

    @Override
    public void tick() {
        LocalPlayer clientPlayer = UltraEffectsUtil.getLocalPlayer();
        if (clientPlayer != null) {
            this.x = clientPlayer.getX();
            this.y = clientPlayer.getY();
            this.z = clientPlayer.getZ();
        }
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }
}
