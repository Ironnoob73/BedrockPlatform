package dev.hail.bedrock_platform.recipe.jei;

import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.block.BPBlocks;
import dev.hail.bedrock_platform.recipe.block_exchange.BERecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static dev.hail.bedrock_platform.recipe.jei.Plugin.getItemStackFromBlockState;

public class BERCategory implements IRecipeCategory<BERecipe> {
    public static final RecipeType<BERecipe> TYPE = RecipeType.create(BedrockPlatform.MODID, "block_exchange", BERecipe.class);
    private final IDrawable icon;
    private final IDrawable slot;
    private final IDrawable plus;
    private final IDrawable arrow;
    public BERCategory(IGuiHelper helper)
    {
        this.icon = helper.createDrawableItemStack(new ItemStack(BPBlocks.BEDROCK_PLATFORM.asItem()));
        this.slot = helper.getSlotDrawable();
        this.plus = helper.getRecipePlusSign();
        this.arrow = helper.getRecipeArrow();
    }
    @Override
    public @NotNull RecipeType<BERecipe> getRecipeType()
    {
        return TYPE;
    }
    @Override
    public @NotNull Component getTitle()
    {
        return Component.translatable("recipe.bedrock_platform.block_exchange.title");
    }

    @Override
    public int getWidth() {
        return 18*5;
    }

    @Override
    public int getHeight() {
        return 18;
    }
    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BERecipe recipe, @NotNull IFocusGroup focuses) {
            builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(recipe.inputItem()).setBackground(this.slot, -1, -1);
            builder.addSlot(RecipeIngredientRole.INPUT, 37, 1).addItemStack(getItemStackFromBlockState(recipe.inputState())).setBackground(this.plus, -16, 1);
            builder.addSlot(RecipeIngredientRole.OUTPUT, 73, 1).addItemStack(getItemStackFromBlockState(recipe.resultState())).setBackground(this.arrow, -22, -1);
    }
}
