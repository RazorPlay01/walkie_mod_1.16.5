package net.razorplay.walkiemod.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.razorplay.walkiemod.WalkieMod;
import net.razorplay.walkiemod.network.packet.c2s.ActivateButtonC2SPacket;
import net.razorplay.walkiemod.network.packet.c2s.ChannelC2SPacket;
import net.razorplay.walkiemod.network.packet.c2s.MuteButtonC2SPacket;

public class ModMessages {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(WalkieMod.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ActivateButtonC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ActivateButtonC2SPacket::new)
                .encoder(ActivateButtonC2SPacket::toBytes)
                .consumer(ActivateButtonC2SPacket::handle)
                .add();
        net.messageBuilder(MuteButtonC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MuteButtonC2SPacket::new)
                .encoder(MuteButtonC2SPacket::toBytes)
                .consumer(MuteButtonC2SPacket::handle)
                .add();
        net.messageBuilder(ChannelC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChannelC2SPacket::new)
                .encoder(ChannelC2SPacket::toBytes)
                .consumer(ChannelC2SPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }
}