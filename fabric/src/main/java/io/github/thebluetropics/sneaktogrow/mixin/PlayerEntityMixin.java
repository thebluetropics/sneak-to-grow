package io.github.thebluetropics.sneaktogrow.mixin;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
  private static boolean isTickProcessed = false;

  @SuppressWarnings("DataFlowIssue")
  @Inject(at = @At("TAIL"), method = "tick()V")
  private void tick(CallbackInfo info) {
    var player = (PlayerEntity) (Object) this;
    var world = player.getWorld();

    if (!world.isClient && player.isSneaking() && !isTickProcessed) {
      BlockPos origin = player.getBlockPos().subtract(new Vec3i(1, 0, 1));

      for (int x = 0; x < 3; x++) {
        for (int z = 0; z < 3; z++) {
          BlockPos blockPos = origin.add(x, 0, z);
          BlockState blockState = world.getBlockState(blockPos);

          if (blockState.getBlock() instanceof SaplingBlock) {
            if (Objects.equals(world.random.nextInt(16), 0)) {
              BoneMealItem.useOnFertilizable(new ItemStack(Items.BONE_MEAL), world, blockPos);
            }
          }

          if (blockState.getBlock() instanceof CropBlock) {
            if (Objects.equals(world.random.nextInt(16), 0)) {
              if (blockState.get(CropBlock.AGE) < CropBlock.MAX_AGE) {
                world.setBlockState(blockPos, blockState.with(CropBlock.AGE, blockState.get(CropBlock.AGE) + 1), Block.NOTIFY_LISTENERS);
              }
            }
          }

          if (blockState.getBlock() instanceof SweetBerryBushBlock) {
            if (blockState.get(SweetBerryBushBlock.AGE) < 3) {
              if (Objects.equals(world.random.nextInt(16), 0)) {
                world.setBlockState(
                  blockPos,
                  blockState.with(SweetBerryBushBlock.AGE, blockState.get(SweetBerryBushBlock.AGE) + 1),
                  Block.NOTIFY_LISTENERS
                );
              }
            }
          }
        }
      }

      isTickProcessed = true;
    }

    if (!world.isClient && !player.isSneaking()) {
      if (isTickProcessed) {
        isTickProcessed = false;
      }
    }
  }
}
