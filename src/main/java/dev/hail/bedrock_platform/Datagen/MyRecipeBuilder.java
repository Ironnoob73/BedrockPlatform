package dev.hail.bedrock_platform.Datagen;

import dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe.BERecipe;
import dev.hail.bedrock_platform.Recipe.BlockReductionRecipe.BRRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class MyRecipeBuilder implements RecipeBuilder {
    protected final ItemStack result;
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    protected String group;
    public MyRecipeBuilder(ItemStack result) {
        this.result = result;
    }
    @Override
    public @NotNull MyRecipeBuilder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }
    @Override
    public @NotNull MyRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }
    @Override
    public @NotNull Item getResult() {
        return this.result.getItem();
    }

    public static class BERBuilder extends MyRecipeBuilder {
        private final BlockState inputState;
        private final Ingredient inputItem;
        private final BlockState resultState;
        public BERBuilder(ItemStack result, BlockState inputState, Ingredient inputItem, BlockState resultState) {
            super(result);
            this.inputState = inputState;
            this.inputItem = inputItem;
            this.resultState = resultState;
        }
        @Override
        public void save(RecipeOutput output, @NotNull ResourceLocation id) {
            Advancement.Builder advancement = output.advancement()
                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                    .rewards(AdvancementRewards.Builder.recipe(id))
                    .requirements(AdvancementRequirements.Strategy.OR);
            this.criteria.forEach(advancement::addCriterion);
            BERecipe recipe = new BERecipe(this.inputState, this.inputItem, this.resultState);
            output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
        }
    }
    public static class BRRBuilder extends MyRecipeBuilder {
        private final BlockState inputState;
        private final Ingredient toolItem;
        private final ItemStack resultItem;
        private final BlockState resultState;
        public BRRBuilder(ItemStack result, BlockState inputState, Ingredient toolItem, ItemStack resultItem, BlockState resultState) {
            super(result);
            this.inputState = inputState;
            this.toolItem = toolItem;
            this.resultItem = resultItem;
            this.resultState = resultState;
        }
        @Override
        public void save(RecipeOutput output, @NotNull ResourceLocation id) {
            Advancement.Builder advancement = output.advancement()
                    .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                    .rewards(AdvancementRewards.Builder.recipe(id))
                    .requirements(AdvancementRequirements.Strategy.OR);
            this.criteria.forEach(advancement::addCriterion);
            BRRecipe recipe = new BRRecipe(this.inputState, this.toolItem, this.resultItem, this.resultState);
            output.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
        }
    }
}