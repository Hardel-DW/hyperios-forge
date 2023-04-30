package com.hardel.hyperios.setup;

import com.hardel.hyperios.HyperiosMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HyperiosMod.MODID);

    public static final RegistryObject<Block> MYTHRIL_BLOCK = registerBlock("mythril_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> TIMELESS_MYTHRIL_BLOCK = registerBlock("timeless_mythril_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DRAGONITE_ORE = registerBlock("dragonite_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DEEPSLATE_DRAGONITE_ORE = registerBlock("deepslate_dragonite_ore",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3.5F, 3.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DRAGONITE_BLOCK = registerBlock("dragonite_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> MYTHRIL_CONDENSER_BLOCK = registerBlock("mythril_condenser",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ItemsRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
