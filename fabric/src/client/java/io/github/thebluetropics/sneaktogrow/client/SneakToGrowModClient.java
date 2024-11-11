package io.github.thebluetropics.sneaktogrow.client;

import io.github.thebluetropics.sneaktogrow.networking.s2c.SneakToGrowPayload;
import io.github.thebluetropics.sneaktogrow.particle.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.SuspendParticle;

public class SneakToGrowModClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ClientPlayNetworking.registerGlobalReceiver(SneakToGrowPayload.ID, (payload, context) -> {
      // TODO: add particles
    });

    ParticleFactoryRegistry.getInstance().register(ModParticles.SNEAK_TO_GROW, SuspendParticle.HappyVillagerFactory::new);
  }
}
