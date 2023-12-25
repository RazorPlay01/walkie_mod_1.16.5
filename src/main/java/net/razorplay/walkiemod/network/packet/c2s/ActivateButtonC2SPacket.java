package net.razorplay.walkiemod.network.packet.c2s;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import net.razorplay.walkiemod.item.custom.WalkieTalkieItem;
import net.razorplay.walkiemod.util.Util;

import java.util.Objects;
import java.util.function.Supplier;

public class ActivateButtonC2SPacket {
    public ActivateButtonC2SPacket() {

    }

    public ActivateButtonC2SPacket(PacketBuffer buf) {

    }

    public void toBytes(PacketBuffer buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayerEntity player = context.getSender();

            ItemStack stack = player.getItemStackFromSlot(Objects.requireNonNull(Util.getHandUse(player)));

            if (!stack.getItem().getClass().equals(WalkieTalkieItem.class)) {
                return;
            }
            stack.getTag().putBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE, !stack.getTag().getBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE));

        });
        return true;
    }
}

