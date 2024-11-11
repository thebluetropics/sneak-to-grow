package io.github.thebluetropics.sneaktogrow.networking;

import io.github.thebluetropics.sneaktogrow.networking.s2c.SneakToGrowPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class ModPayloadTypes {
  public static void register() {
    PayloadTypeRegistry.playS2C().register(SneakToGrowPayload.ID, SneakToGrowPayload.PACKET_CODEC);
  }
}
