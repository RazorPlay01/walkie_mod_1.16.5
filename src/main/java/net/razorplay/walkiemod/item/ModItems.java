package net.razorplay.walkiemod.item;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.razorplay.walkiemod.WalkieMod;
import net.razorplay.walkiemod.item.custom.WalkieTalkieItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WalkieMod.MOD_ID);

    public static final RegistryObject<Item> WALKIE_TALKIE = ITEMS.register("walkietalkie",
            () -> new WalkieTalkieItem(new Item.Properties().group(ModItemGroup.WALKIEMOD_GROUP)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
