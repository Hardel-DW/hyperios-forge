package com.hardel.hyperios.common.integration;

import com.hardel.hyperios.HyperiosMod;
import com.hardel.hyperios.common.recipe.MythrilCondenserRecipe;
import com.hardel.hyperios.setup.BlockRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MythrilCondenserRecipeCategory implements IRecipeCategory<MythrilCondenserRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(HyperiosMod.MODID, "mythril_condenser");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(HyperiosMod.MODID, "textures/gui/jei/mythril_condenser.png");

    private final IDrawable background;
    private final IDrawable icon;

    public MythrilCondenserRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 150);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.MYTHRIL_CONDENSER_BLOCK.get()));
    }

    @Override
    public @NotNull RecipeType<MythrilCondenserRecipe> getRecipeType() {
        return JEIHyperiosModPlugin.MYTHRIL_CONDENSER_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("jei.hyperios.mythril_condenser");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MythrilCondenserRecipe recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> ingredient = recipe.getIngredients();

        List<IRecipeSlotBuilder> slots = new ArrayList<>();
        slots.add(builder.addSlot(RecipeIngredientRole.INPUT, 79, 16));
        slots.add(builder.addSlot(RecipeIngredientRole.INPUT, 130, 53));
        slots.add(builder.addSlot(RecipeIngredientRole.INPUT, 111, 111));
        slots.add(builder.addSlot(RecipeIngredientRole.INPUT, 49, 111));
        slots.add(builder.addSlot(RecipeIngredientRole.INPUT, 29, 54));

        for (int i = 0; i < ingredient.size(); i++) {
            slots.get(i).addItemStack(ingredient.get(i).getItems()[0]);
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 78, 67).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
        builder.setShapeless();
    }
}
