package dev.hail.bedrock_platform.Recipe.JEI;

import dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe.BERecipe;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.common.util.ErrorUtil;
import mezz.jei.library.plugins.vanilla.crafting.CategoryRecipeValidator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.*;

import java.util.List;

public final class BPRecipes {

    private final RecipeManager recipeManager;
    private final IIngredientManager ingredientManager;

    public BPRecipes(IIngredientManager ingredientManager) {
        Minecraft minecraft = Minecraft.getInstance();
        ErrorUtil.checkNotNull(minecraft, "minecraft");
        ClientLevel world = minecraft.level;
        ErrorUtil.checkNotNull(world, "minecraft world");
        this.recipeManager = world.getRecipeManager();
        this.ingredientManager = ingredientManager;
    }
    public List<RecipeHolder<BERecipe>> getBERecipes(IRecipeCategory<RecipeHolder<BERecipe>> BERCategory) {
        CategoryRecipeValidator<BERecipe> validator = new CategoryRecipeValidator(BERCategory, this.ingredientManager, 1);
        return getValidHandledRecipes(this.recipeManager, BERecipe.BLOCK_EXCHANGE.get(), validator);
    }

    private static <C extends RecipeInput, T extends Recipe<C>> List<RecipeHolder<T>> getValidHandledRecipes(RecipeManager recipeManager, RecipeType<T> recipeType, CategoryRecipeValidator<T> validator) {
        return recipeManager.getAllRecipesFor(recipeType).stream().filter((r) -> {
            return validator.isRecipeValid(r) && validator.isRecipeHandled(r);
        }).toList();
    }
}
