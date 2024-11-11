package io.github.thebluetropics.sneaktogrow;

import io.github.thebluetropics.sneaktogrow.networking.ModPayloadTypes;
import io.github.thebluetropics.sneaktogrow.particle.ModParticles;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SneakToGrowMod implements ModInitializer {
  public static final String ID = "sneaktogrow";
  public static final Logger LOGGER = LoggerFactory.getLogger(ID);

  @Override
  public void onInitialize() {
    ModPayloadTypes.register();
    ModParticles.initialize();
  }
}
