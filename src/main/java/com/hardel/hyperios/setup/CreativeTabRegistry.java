package com.hardel.hyperios.setup;

import com.hardel.hyperios.HyperiosMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.hardel.hyperios.setup.BlockRegistry.MYTHRIL_BLOCK;
import static com.hardel.hyperios.setup.ItemsRegistry.EDEN_ETERNAL;

@Mod.EventBusSubscriber(modid = HyperiosMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTabRegistry {

    public static CreativeModeTab HYPERLIOS_ITEMS;
    public static CreativeModeTab HYPERLIOS_BLOCKS;


    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        HYPERLIOS_ITEMS = event.registerCreativeModeTab(new ResourceLocation(HyperiosMod.MODID, "hyperios_items"),
                builder -> builder.icon(() -> new ItemStack(EDEN_ETERNAL.get())).title(Component.translatable("itemGroup.hyperios.hyperios_items")));

        HYPERLIOS_BLOCKS = event.registerCreativeModeTab(new ResourceLocation(HyperiosMod.MODID, "hyperios_blocks"),
                builder -> builder.icon(() -> new ItemStack(MYTHRIL_BLOCK.get())).title(Component.translatable("itemGroup.hyperios.hyperios_blocks")));
    }
}
