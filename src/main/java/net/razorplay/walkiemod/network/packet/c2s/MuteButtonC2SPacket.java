package net.razorplay.walkiemod.network.packet.c2s;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.razorplay.walkiemod.client.gui.screen.WalkieTalkieScreen;
import net.razorplay.walkiemod.item.custom.WalkieTalkieItem;
import net.razorplay.walkiemod.network.ModMessages;
import net.razorplay.walkiemod.util.Util;

import java.util.Objects;
import java.util.function.Supplier;

public class MuteButtonC2SPacket {
    public MuteButtonC2SPacket() {

    }

    public MuteButtonC2SPacket(PacketBuffer buf) {

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
            stack.getTag().putBoolean(WalkieTalkieItem.NBT_KEY_MUTE, !stack.getTag().getBoolean(WalkieTalkieItem.NBT_KEY_MUTE));

        });
        return true;
    }
}

