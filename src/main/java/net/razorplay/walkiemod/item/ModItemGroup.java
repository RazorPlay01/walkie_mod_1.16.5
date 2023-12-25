package net.razorplay.walkiemod.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {
    public static final ItemGroup WALKIEMOD_GROUP = new ItemGroup("walkiemod_group") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.WALKIE_TALKIE.get());
        }
    };
}