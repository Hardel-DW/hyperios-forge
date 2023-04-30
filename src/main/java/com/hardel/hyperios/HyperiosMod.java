package com.hardel.hyperios;

import com.hardel.hyperios.setup.BlockEntityRegistry;
import com.hardel.hyperios.setup.BlockRegistry;
import com.hardel.hyperios.setup.ItemsRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.hardel.hyperios.setup.CreativeTabRegistry.HYPERLIOS_BLOCKS;
import static com.hardel.hyperios.setup.CreativeTabRegistry.HYPERLIOS_ITEMS;

@Mod(HyperiosMod.MODID)
public class HyperiosMod {
    public static final String MODID = "hyperios";
    private static final Logger LOGGER = LogUtils.getLogger();

    public HyperiosMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        ItemsRegistry.register(modEventBus);
        BlockRegistry.register(modEventBus);
        BlockEntityRegistry.register(modEventBus);

        modEventBus.addListener(this::addCreative);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == HYPERLIOS_ITEMS) {
            event.accept(ItemsRegistry.EDEN_ETERNAL);
            event.accept(ItemsRegistry.UNPOWERED_EDEN_ETERNAL);
            event.accept(ItemsRegistry.MYTHRIL);
            event.accept(ItemsRegistry.TIMELESS_MYTHRIL);
            event.accept(ItemsRegistry.ECHO_CLOCK);
            event.accept(ItemsRegistry.RAW_DRAGONITE);
            event.accept(ItemsRegistry.DRAGONITE_INGOT);
        }

        if (event.getTab() == HYPERLIOS_BLOCKS) {
            event.accept(BlockRegistry.MYTHRIL_BLOCK);
            event.accept(BlockRegistry.TIMELESS_MYTHRIL_BLOCK);
            event.accept(BlockRegistry.DRAGONITE_ORE);
            event.accept(BlockRegistry.DEEPSLATE_DRAGONITE_ORE);
            event.accept(BlockRegistry.DRAGONITE_BLOCK);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HyperiosMod: commonSetup");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
