package com.hardel.hyperios.setup;

import com.hardel.hyperios.HyperiosMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemsRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HyperiosMod.MODID);

    public static final RegistryObject<Item> EDEN_ETERNAL = ITEMS.register("eden_eternal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> UNPOWERED_EDEN_ETERNAL = ITEMS.register("unpowered_eden_eternal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MYTHRIL = ITEMS.register("mythril", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TIMELESS_MYTHRIL = ITEMS.register("timeless_mythril", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ECHO_CLOCK = ITEMS.register("echo_clock", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_DRAGONITE = ITEMS.register("raw_dragonite", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DRAGONITE_INGOT = ITEMS.register("dragonite_ingot", () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
