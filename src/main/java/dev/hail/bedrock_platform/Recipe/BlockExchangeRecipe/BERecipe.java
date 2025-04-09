package dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe;

import dev.hail.bedrock_platform.BedrockPlatform;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
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

public record BERecipe(BlockState inputState, Ingredient inputItem, BlockState resultState) implements Recipe<BERInput> {
    // Add a constructor that sets all properties.

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(this.inputItem);
        return list;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public boolean matches(BERInput input, @NotNull Level level) {
        return this.inputState == input.state() && this.inputItem.test(input.stack());
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return resultState.getBlock().asItem().getDefaultInstance();
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull BERInput input, HolderLookup.@NotNull Provider registries) {
        return resultState.getBlock().asItem().getDefaultInstance();
    }

    public @NotNull BlockState assembleBlock(RegistryAccess registryAccess) {
        return this.resultState;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return BLOCK_EXCHANGE.get();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return BERSerializer.BLOCK_EXCHANGE.get();
    }

    // Register
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, BedrockPlatform.MODID);

    public static final Supplier<RecipeType<BERecipe>> BLOCK_EXCHANGE =
            RECIPE_TYPES.register(
                    "block_ex",
                    // We need the qualifying generic here due to generics being generics.
                    () -> RecipeType.simple(BedrockPlatform.modResLocation("block_exchange"))
            );
}