package dev.hail.bedrock_platform._datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;

import java.util.concurrent.CompletableFuture;

public class MyRecipeProvider extends RecipeProvider {
    // Get the parameters from GatherDataEvent.
    public MyRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        new BERBuilder(
                // Our constructor parameters. This example adds the ever-popular dirt -> diamond conversion.
                new ItemStack(Items.DIAMOND),
                Blocks.END_PORTAL_FRAME.defaultBlockState().rotate(Rotation.CLOCKWISE_90),
                Ingredient.of(Items.APPLE),
                Blocks.GLASS.defaultBlockState()
        )
                .unlockedBy("has_apple", has(Items.APPLE))
                .save(output);
        // other recipe builders here
    }
}