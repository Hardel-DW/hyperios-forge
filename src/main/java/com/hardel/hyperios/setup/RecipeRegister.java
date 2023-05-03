package com.hardel.hyperios.setup;

import com.hardel.hyperios.HyperiosMod;
import com.hardel.hyperios.common.recipe.MythrilCondenserRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeRegister {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, HyperiosMod.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, HyperiosMod.MODID);

    public static final RegistryObject<RecipeSerializer<?>> MYTHRIL_CONDENSER_RECIPE_SERIALIZER = SERIALIZER.register("mythril_condenser", () -> MythrilCondenserRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeType<MythrilCondenserRecipe>> MYTHRIL_CONDENSER8type = RECIPE_TYPES.register("mythril_condenser", () -> MythrilCondenserRecipe.Type.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZER.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
