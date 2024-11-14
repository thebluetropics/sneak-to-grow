package io.github.thebluetropics.sneaktogrow.mixin;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
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
      BlockPos origin = player.getBlockPos().subtract(new Vec3i(1, 1, 1));

      HashSet<BlockPos> growableBlockPosSet = new HashSet<>();

      for (int x = 0; x < 3; x++) {
        for (int y = 0; y < 3; y++) {
          for (int z = 0; z < 3; z++) {
            BlockPos blockPos = origin.add(x, y, z);
            BlockState blockState = world.getBlockState(blockPos);

            if (blockState.getBlock() instanceof CropBlock) {
              if (blockState.get(CropBlock.AGE) < CropBlock.MAX_AGE) {
                growableBlockPosSet.add(blockPos);
              }
            }

            if (blockState.getBlock() instanceof SaplingBlock) {
              growableBlockPosSet.add(blockPos);
            }

            if (blockState.getBlock() instanceof SweetBerryBushBlock) {
              if (blockState.get(SweetBerryBushBlock.AGE) < SweetBerryBushBlock.MAX_AGE) {
                growableBlockPosSet.add(blockPos);
              }
            }
          }
        }
      }

      if (!growableBlockPosSet.isEmpty()) {
        BlockPos selectedBlockPos = growableBlockPosSet.toArray(new BlockPos[]{})[world.getRandom().nextInt(growableBlockPosSet.size())];
        BlockState selectedBlockState = world.getBlockState(selectedBlockPos);

        if (selectedBlockState.getBlock() instanceof CropBlock) {
          if (Objects.equals(world.random.nextInt(16), 0)) {
            world.setBlockState(selectedBlockPos, selectedBlockState.with(CropBlock.AGE, selectedBlockState.get(CropBlock.AGE) + 1), Block.NOTIFY_LISTENERS);
          }
        }

        if (selectedBlockState.getBlock() instanceof SaplingBlock) {
          if (Objects.equals(world.random.nextInt(24), 0)) {
            BoneMealItem.useOnFertilizable(new ItemStack(Items.BONE_MEAL), world, selectedBlockPos);
          }
        }

        if (selectedBlockState.getBlock() instanceof SweetBerryBushBlock) {
          if (Objects.equals(world.random.nextInt(24), 0)) {
            world.setBlockState(
              selectedBlockPos,
              selectedBlockState.with(SweetBerryBushBlock.AGE, selectedBlockState.get(SweetBerryBushBlock.AGE) + 1),
              Block.NOTIFY_LISTENERS
            );
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
