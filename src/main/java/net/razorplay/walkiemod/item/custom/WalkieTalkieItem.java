package net.razorplay.walkiemod.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.razorplay.walkiemod.client.gui.screen.WalkieTalkieScreen;

public class WalkieTalkieItem extends Item {
    public static final String NBT_KEY_CANAL = "walkietalkie.canal";
    public static final String NBT_KEY_MUTE = "walkietalkie.mute";
    public static final String NBT_KEY_ACTIVATE = "walkietalkie.activate";

    public WalkieTalkieItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn) {
        if (player.world.isRemote) {
            if (player.getHeldItem(handIn).hasTag()){
                ItemStack item = player.getHeldItem(handIn);
                new WalkieTalkieScreen(item);
            }
        }
        return super.onItemRightClick(worldIn, player, handIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn.world.isRemote) {
            return;
        }

        if (!stack.hasTag()) {
            CompoundNBT tag = stack.getOrCreateTag();
            tag.putBoolean(NBT_KEY_ACTIVATE, false);
            tag.putBoolean(NBT_KEY_MUTE, false);
            tag.putInt(NBT_KEY_CANAL, 0);
            stack.setTag(tag);
        }
    }
}
