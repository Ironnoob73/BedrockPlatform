package dev.hail.bedrock_platform.recipe.block_reduction;

import dev.hail.bedrock_platform.BedrockPlatform;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public record BRRecipe(BlockState inputState, Ingredient decompositionProducts, ItemStack result, BlockState resultState) implements Recipe<BRRInput> {
    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.decompositionProducts);
        return list;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public boolean matches(@NotNull BRRInput input, @NotNull Level level) {
        return this.inputState == input.state();
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return this.result;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull BRRInput input, HolderLookup.@NotNull Provider registries) {
        return this.result.copy();
    }

    public @NotNull BlockState assembleBlock(HolderLookup.@NotNull Provider registries) {
        return this.resultState;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return BLOCK_REDUCTION.get();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return BRRSerializer.BLOCK_REDUCTION.get();
    }

    // Register
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, BedrockPlatform.MODID);

    public static final Supplier<RecipeType<BRRecipe>> BLOCK_REDUCTION =
            RECIPE_TYPES.register(
                    "block_re",
                    // We need the qualifying generic here due to generics being generics.
                    () -> RecipeType.simple(BedrockPlatform.modResLocation("block_reduction"))
            );
}