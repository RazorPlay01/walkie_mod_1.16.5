package net.razorplay.walkiemod.util;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.razorplay.walkiemod.item.custom.WalkieTalkieItem;

public class Util {
    public static EquipmentSlotType getHandUse(ServerPlayerEntity player) {

        ItemStack mainHand = player.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
        ItemStack offHand = player.getItemStackFromSlot(EquipmentSlotType.OFFHAND);

        if (mainHand.getItem() instanceof WalkieTalkieItem) {
            return EquipmentSlotType.MAINHAND;
        }
        if (offHand.getItem() instanceof WalkieTalkieItem) {
            return EquipmentSlotType.OFFHAND;
        }
        return null;
    }
    public static EquipmentSlotType getHandUse(ClientPlayerEntity player) {

        ItemStack mainHand = player.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
        ItemStack offHand = player.getItemStackFromSlot(EquipmentSlotType.OFFHAND);

        if (mainHand.getItem() instanceof WalkieTalkieItem) {
            return EquipmentSlotType.MAINHAND;
        }
        if (offHand.getItem() instanceof WalkieTalkieItem) {
            return EquipmentSlotType.OFFHAND;
        }
        return null;
    }
}
