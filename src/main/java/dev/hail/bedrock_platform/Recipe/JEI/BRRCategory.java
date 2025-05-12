package dev.hail.bedrock_platform.Recipe.JEI;

import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.Items.BPItems;
import dev.hail.bedrock_platform.Recipe.BlockReductionRecipe.BRRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static dev.hail.bedrock_platform.Recipe.JEI.Plugin.getItemStackFromBlockState;

public class BRRCategory implements IRecipeCategory<BRRecipe> {
    public static final RecipeType<BRRecipe> TYPE = RecipeType.create(BedrockPlatform.MODID, "block_reduction", BRRecipe.class);
    private final IDrawable icon;
    private final IDrawable slot;
    private final IDrawable arrow;
    public BRRCategory(IGuiHelper helper)
    {
        this.slot = helper.getSlotDrawable();
        this.icon = helper.createDrawableItemStack(new ItemStack(BPItems.OBSIDIAN_WRENCH.get()));
        this.arrow = helper.getRecipeArrow();
    }
    @Override
    public @NotNull RecipeType<BRRecipe> getRecipeType()
    {
        return TYPE;
    }
    @Override
    public @NotNull Component getTitle()
    {
        return Component.translatable("recipe.bedrock_platform.block_reduction.title");
    }

    @Override
    public int getWidth() {
        return 18*4;
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
    public void setRecipe(IRecipeLayoutBuilder builder, BRRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addItemStack(getItemStackFromBlockState(recipe.inputState())).setBackground(this.arrow, 12, -1);
        ItemStack decompositionProducts = recipe.getIngredients().get(0).getItems()[0];
        decompositionProducts.set(DataComponents.LORE,new ItemLore(List.of(Component.translatable("tooltip.bedrock_platform.decomposition_products").withStyle(ChatFormatting.GRAY))));
        builder.addSlot(RecipeIngredientRole.INPUT, 37-18, 1).addItemStack(decompositionProducts);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 37, 1).addItemStack(recipe.result()).setBackground(this.slot, -1, -1);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 37+18, 1).addItemStack(getItemStackFromBlockState(recipe.resultState()));
    }
}
