package net.razorplay.walkiemod;

import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import de.maxhenkel.voicechat.api.opus.OpusDecoder;
import de.maxhenkel.voicechat.api.opus.OpusEncoder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.razorplay.walkiemod.item.custom.WalkieTalkieItem;

import java.util.ArrayList;
import java.util.Objects;


@ForgeVoicechatPlugin
public class WalkieTalkieVoiceChatPlugin implements VoicechatPlugin {

    public static VoicechatServerApi voicechatServerApi;
    public static OpusDecoder opusDecoder;
    public static OpusEncoder opusEncoder;

    @Override
    public String getPluginId() {
        return WalkieMod.MOD_ID;
    }

    @Override
    public void initialize(VoicechatApi api) {
        VoicechatPlugin.super.initialize(api);
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(MicrophonePacketEvent.class, this::onMicPacket);
        registration.registerEvent(VoicechatServerStartedEvent.class, this::onServerStarted);
    }

    private void onServerStarted(VoicechatServerStartedEvent event) {
        voicechatServerApi = event.getVoicechat();
        opusDecoder = voicechatServerApi.createDecoder();
        opusEncoder = voicechatServerApi.createEncoder();
    }

    private void onMicPacket(MicrophonePacketEvent event) {
        VoicechatConnection senderConnection = event.getSenderConnection();

        if (senderConnection == null) {
            return;
        }

        if (!(senderConnection.getPlayer().getPlayer() instanceof PlayerEntity)) {
            return;
        }

        if (senderConnection.getPlayer().getPlayer() != null) {
            PlayerEntity senderPlayer = (PlayerEntity) senderConnection.getPlayer().getPlayer();
            ItemStack senderStack = getWalkieTalkieActivate(senderPlayer);

            if (getWalkieTalkieActivate(senderPlayer) == null) {
                return;
            }

            if (isWalkieTalkieMute(senderStack)) {
                return;
            }

            int senderChannel = getChannel(senderStack);

            for (PlayerEntity receiverPlayer : Objects.requireNonNull(senderPlayer.getServer()).getPlayerList().getPlayers()) {

                if (receiverPlayer.getUniqueID().equals(senderPlayer.getUniqueID())) {
                    continue;
                }

                ItemStack receiverStack = getWalkieTalkieActivate(receiverPlayer);

                if (receiverStack == null) {
                    continue;
                }

                int receiverChannel = getChannel(receiverStack);

                if (receiverChannel != senderChannel) {
                    continue;
                }

                // Send audio
                VoicechatConnection connection = voicechatServerApi.getConnectionOf(receiverPlayer.getUniqueID());
                if (connection == null) {
                    continue;
                }

                voicechatServerApi.sendStaticSoundPacketTo(connection, event.getPacket().staticSoundPacketBuilder().build());
            }
        }
    }


    private ItemStack getWalkieTalkieActivate(PlayerEntity player) {

        ItemStack itemStack = null;

        PlayerInventory playerInventory = player.inventory;

        ArrayList<ItemStack> inventory = new ArrayList<>();
        inventory.addAll(playerInventory.mainInventory);
        inventory.addAll(playerInventory.armorInventory);
        inventory.addAll(playerInventory.offHandInventory);

        for (ItemStack item : inventory) {
            CompoundNBT tag = item.getOrCreateTag();
            if (item.getItem() instanceof WalkieTalkieItem) {
                if (tag.getBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE)) {
                    itemStack = item;
                }
            }
        }
        return itemStack;
    }

    private int getChannel(ItemStack stack) {
        CompoundNBT tag = stack.getOrCreateTag();
        return tag.getInt(WalkieTalkieItem.NBT_KEY_CANAL);
    }

    private boolean isWalkieTalkieMute(ItemStack stack) {
        CompoundNBT tag = stack.getOrCreateTag();
        return tag.getBoolean(WalkieTalkieItem.NBT_KEY_MUTE);
    }
}
