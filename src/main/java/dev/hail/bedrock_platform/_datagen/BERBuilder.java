package dev.hail.bedrock_platform._datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;
import dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe.BERecipe;

public class BERBuilder extends BaseRecipeBuilder {
    private final BlockState inputState;
    private final Ingredient inputItem;
    private final BlockState resultState;

    // Since we have exactly one of each input, we pass them to the constructor.
    // Builders for recipe serializers that have ingredient lists of some sort would usually
    // initialize an empty list and have #addIngredient or similar methods instead.
    public BERBuilder(ItemStack result, BlockState inputState, Ingredient inputItem, BlockState resultState) {
        super(result);
        this.inputState = inputState;
        this.inputItem = inputItem;
        this.resultState = resultState;
    }

    // Saves a recipe using the given RecipeOutput and id. This method is defined in the RecipeBuilder interface.
    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        // Build the advancement.
        Advancement.Builder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        // Our factory parameters are the result, the block state, and the ingredient.
        BERecipe recipe = new BERecipe(this.inputState, this.inputItem, this.resultState);
        // Pass the id, the recipe, and the recipe advancement into the RecipeOutput.
        output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}
