package io.github.thebluetropics.sneaktogrow.client;

import io.github.thebluetropics.sneaktogrow.networking.s2c.SneakToGrowPayload;
import io.github.thebluetropics.sneaktogrow.particle.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.SuspendParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;

import java.util.List;

import static java.util.Objects.isNull;

public class SneakToGrowModClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    ClientPlayNetworking.registerGlobalReceiver(SneakToGrowPayload.ID, (payload, context) -> {
      ClientWorld world = MinecraftClient.getInstance().world;

      if (isNull(world)) {
        return;
      }

      BlockPos blockPos = payload.blockPos();
      BlockState blockState = world.getBlockState(blockPos);

      VoxelShape shape = blockState.getOutlineShape(world, blockPos);
      List<Box> shapeBoundingBoxes = shape.getBoundingBoxes();

      for (int i = 0; i < 1; i++) {
        Box box = shapeBoundingBoxes.get(world.getRandom().nextInt(shapeBoundingBoxes.size()));

        world.addParticle(
          ModParticles.SNEAK_TO_GROW,
          blockPos.getX() + box.minX + (world.getRandom().nextFloat() * box.maxX),
          blockPos.getY() + box.minY + (world.getRandom().nextFloat() * box.maxY),
          blockPos.getZ() + box.minZ + (world.getRandom().nextFloat() * box.maxZ),
          0.0f,
          0.0f,
          0.0f
        );
      }

      if (payload.shouldPlaySoundEffect()) {
        world.playSoundAtBlockCenter(blockPos, SoundEvents.ITEM_BONE_MEAL_USE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
      }
    });

    ParticleFactoryRegistry.getInstance().register(ModParticles.SNEAK_TO_GROW, SuspendParticle.HappyVillagerFactory::new);
  }
}
