package com.hardel.hyperios.setup;

import com.hardel.hyperios.HyperiosMod;
import com.hardel.hyperios.common.block.entity.MythrilCondenserBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, HyperiosMod.MODID);

    public static final RegistryObject<BlockEntityType<MythrilCondenserBlockEntity>> MYTHRIL_CONDENSER = BLOCK_ENTITIES.register("mythril_condenser",
            () -> BlockEntityType.Builder.of(MythrilCondenserBlockEntity::new, BlockRegistry.MYTHRIL_CONDENSER_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
