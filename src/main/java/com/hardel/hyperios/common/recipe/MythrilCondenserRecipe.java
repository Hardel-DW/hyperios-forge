package com.hardel.hyperios.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hardel.hyperios.HyperiosMod;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MythrilCondenserRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public MythrilCondenserRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer container, @NotNull Level level) {
        if (level.isClientSide()) {
            return false;
        }

        List<ItemStack> slots = new ArrayList<>();
        NonNullList<Ingredient> ingredients = NonNullList.create();

        for (int i = 0; i < 5; i++) {
            slots.add(container.getItem(i));
            ingredients.add(recipeItems.get(i));
        }

        for (int i = 0; i < slots.size(); i++) {
            if (!recipeItems.get(i).test(slots.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer container, @NotNull RegistryAccess registryAccess) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<MythrilCondenserRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
        public static final String ID = "mythril_condenser";
    }

    public static class Serializer implements RecipeSerializer<MythrilCondenserRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(HyperiosMod.MODID, "mythril_condenser");

        @Override
        public @NotNull MythrilCondenserRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject jsonObject) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));

            JsonArray ingredientsJson = GsonHelper.getAsJsonArray(jsonObject, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredientsJson.size(), Ingredient.EMPTY);

            for (int i = 0; i < ingredientsJson.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredientsJson.get(i)));
            }

            return new MythrilCondenserRecipe(id, output, inputs);
        }

        @Override
        public @Nullable MythrilCondenserRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.fromNetwork(buf));

            ItemStack output = buf.readItem();
            return new MythrilCondenserRecipe(id, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, MythrilCondenserRecipe recipe) {
            buf.writeInt(recipe.recipeItems.size());

            for (Ingredient ingredient : recipe.recipeItems) {
                ingredient.toNetwork(buf);
            }

            buf.writeItemStack(recipe.output, false);
        }
    }
}
