package dev.hail.bedrock_platform.BlockExchangeRecipe;

import dev.hail.bedrock_platform.BedrockPlatform;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BERecipe implements Recipe<BERInput> {
    // https://docs.neoforged.net/docs/1.21.1/resources/server/recipes/#custom-recipes
    // An in-code representation of our recipe data. This can be basically anything you want.
    // Common things to have here is a processing time integer of some kind, or an experience reward.
    // Note that we now use an ingredient instead of an item stack for the input.
    private final BlockState inputState;
    private final Ingredient inputItem;
    //private final ItemStack result;
    private final BlockState resultState;
    // Add a constructor that sets all properties.
    public BERecipe(BlockState inputState, Ingredient inputItem, BlockState resultState) {
        this.inputState = inputState;
        this.inputItem = inputItem;
        //this.result = result;
        this.resultState = resultState;
    }
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
    public ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return null;
    }
    @Override
    public ItemStack assemble(@NotNull BERInput input, HolderLookup.@NotNull Provider registries) {
        return null;
    }
    public @NotNull BlockState assembleBlock(@NotNull BERInput input, HolderLookup.@NotNull Provider registries){
        return this.resultState;
    }
    @Override
    public RecipeType<?> getType() {
        return BLOCK_EXCHANGE.get();
    }

    public BlockState getInputState() {
        return inputState;
    }

    public Ingredient getInputItem() {
        return inputItem;
    }

    public BlockState getResultState() {
        return inputState;
    }
    @Override
    public RecipeSerializer<?> getSerializer() {
        return BERSerializer.BLOCK_EXCHANGE.get();
    }

    // Register
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, BedrockPlatform.MODID);

    public static final Supplier<RecipeType<BERecipe>> BLOCK_EXCHANGE =
            RECIPE_TYPES.register(
                    "right_click_block",
                    // We need the qualifying generic here due to generics being generics.
                    () -> RecipeType.<BERecipe>simple(ResourceLocation.fromNamespaceAndPath(BedrockPlatform.MODID, "block_exchange"))
            );
}