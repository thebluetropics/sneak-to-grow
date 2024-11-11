package io.github.thebluetropics.sneaktogrow.particle;

import io.github.thebluetropics.sneaktogrow.SneakToGrowMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
  public static final SimpleParticleType SNEAK_TO_GROW = Registry.register(
    Registries.PARTICLE_TYPE,
    Identifier.of(SneakToGrowMod.ID, "sneak_to_grow"),
    FabricParticleTypes.simple()
  );

  public static void initialize() { /* ... */ }
}
