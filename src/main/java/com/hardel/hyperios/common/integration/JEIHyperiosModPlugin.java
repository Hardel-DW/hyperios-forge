package com.hardel.hyperios.common.integration;

import com.hardel.hyperios.HyperiosMod;
import com.hardel.hyperios.client.screen.MythrilCondenserScreen;
import com.hardel.hyperios.common.recipe.MythrilCondenserRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIHyperiosModPlugin implements IModPlugin {
    public static RecipeType<MythrilCondenserRecipe> MYTHRIL_CONDENSER_TYPE = new RecipeType<>(MythrilCondenserRecipeCategory.UID, MythrilCondenserRecipe.class);


    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(HyperiosMod.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MythrilCondenserRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<MythrilCondenserRecipe> recipes = recipeManager.getAllRecipesFor(MythrilCondenserRecipe.Type.INSTANCE);
        registration.addRecipes(MYTHRIL_CONDENSER_TYPE, recipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MythrilCondenserScreen.class, 2, 2, 70, 31, JEIHyperiosModPlugin.MYTHRIL_CONDENSER_TYPE);
    }
}
