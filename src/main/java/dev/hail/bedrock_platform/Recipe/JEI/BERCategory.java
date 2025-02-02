package dev.hail.bedrock_platform.Recipe.JEI;

import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.Blocks.BPBlocks;
import dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe.BERecipe;
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
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static dev.hail.bedrock_platform.Recipe.JEI.Plugin.getItemStackFromBlockState;

public class BERCategory implements IRecipeCategory<BERecipe> {
    public static final RecipeType<BERecipe> TYPE = RecipeType.create(BedrockPlatform.MODID, "block_exchange", BERecipe.class);
    private final IDrawable background;
    private final IDrawable icon;
    public BERCategory(IGuiHelper helper)
    {
        this.background = helper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 0, 168, 125, 18).build();
        this.icon = helper.createDrawableItemStack(new ItemStack(BPBlocks.BEDROCK_PLATFORM_ITEM.get()));
    }
    @Override
    public RecipeType<BERecipe> getRecipeType()
    {
        return TYPE;
    }
    @Override
    public Component getTitle()
    {
        return Component.translatable("recipe.bedrock_platform.block_exchange.title");
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
    public void setRecipe(IRecipeLayoutBuilder builder, BERecipe recipe, @NotNull IFocusGroup focuses) {
            builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(recipe.getInputItem());
            builder.addSlot(RecipeIngredientRole.INPUT, 50, 1).addItemStack(getItemStackFromBlockState(recipe.getInputState()));
            builder.addSlot(RecipeIngredientRole.OUTPUT, 108, 1).addItemStack(getItemStackFromBlockState(recipe.getResultState()));
    }
}
