package io.github.thebluetropics.sneaktogrow.networking.s2c;

import io.github.thebluetropics.sneaktogrow.SneakToGrowMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record SneakToGrowPayload (
  BlockPos blockPos
) implements CustomPayload {
  public static final CustomPayload.Id<SneakToGrowPayload> ID = new CustomPayload.Id<>(
    Identifier.of(SneakToGrowMod.ID, "sneak_to_grow")
  );
  public static final PacketCodec<RegistryByteBuf, SneakToGrowPayload> PACKET_CODEC = PacketCodec.tuple(
    BlockPos.PACKET_CODEC, SneakToGrowPayload::blockPos,
    SneakToGrowPayload::new
  );

  @Override
  public Id<? extends CustomPayload> getId() {
    return ID;
  }
}
