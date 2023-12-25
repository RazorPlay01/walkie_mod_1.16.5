package net.razorplay.walkiemod.network.packet.c2s;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.razorplay.walkiemod.item.custom.WalkieTalkieItem;
import net.razorplay.walkiemod.util.Util;

import java.util.Objects;
import java.util.function.Supplier;

public class ChannelC2SPacket {
    private String msg;
    public ChannelC2SPacket(String msg) {
        this.msg = msg;
    }

    public ChannelC2SPacket(PacketBuffer buf) {
        this.msg = buf.readString(32767);
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeString(this.msg);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayerEntity player = context.getSender();

            ItemStack stack = player.getItemStackFromSlot(Objects.requireNonNull(Util.getHandUse(player)));

            if (!stack.getItem().getClass().equals(WalkieTalkieItem.class)) {
                return;
            }
            stack.getTag().putInt(WalkieTalkieItem.NBT_KEY_CANAL, Integer.parseInt(this.msg));

        });
        return true;
    }
}

