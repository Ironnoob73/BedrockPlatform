package dev.hail.bedrock_platform.Recipe.JEI;

import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.Blocks.BPBlocks;
import dev.hail.bedrock_platform.Items.BPItems;
import dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe.BERecipe;
import dev.hail.bedrock_platform.Recipe.BlockReductionRecipe.BRRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.common.Constants;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static dev.hail.bedrock_platform.Recipe.JEI.Plugin.getItemStackFromBlockState;

public class BRRCategory implements IRecipeCategory<BRRecipe> {
    public static final RecipeType<BRRecipe> TYPE = RecipeType.create(BedrockPlatform.MODID, "block_reduction", BRRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;
    public BRRCategory(IGuiHelper helper)
    {
        this.background = helper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 49, 168, 76+18, 18).build();
        this.slot = helper.getSlotDrawable();
        this.icon = helper.createDrawableItemStack(new ItemStack(BPItems.OBSIDIAN_WRENCH.get()));
    }
    @Override
    public RecipeType<BRRecipe> getRecipeType()
    {
        return TYPE;
    }
    @Override
    public Component getTitle()
    {
        return Component.translatable("recipe.bedrock_platform.block_reduction.title");
    }

    @Override
    public IDrawable getBackground()
    {
        return this.background;
    }
    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BRRecipe recipe, @NotNull IFocusGroup focuses) {
            //builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(recipe.getInputItem());
            builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addItemStack(getItemStackFromBlockState(recipe.getInputState()));
            builder.addSlot(RecipeIngredientRole.OUTPUT, 59, 1).addItemStack(recipe.getResult());
            builder.addSlot(RecipeIngredientRole.OUTPUT, 77, 1).addItemStack(getItemStackFromBlockState(recipe.getResultState())).setBackground(this.slot, -1, -1);
    }
}
