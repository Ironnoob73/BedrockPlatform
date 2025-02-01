package dev.hail.bedrock_platform.Recipe.JEI;

import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.Items.BPItems;
import dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe.BERecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class Plugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid()
    {
        return ResourceLocation.fromNamespaceAndPath(BedrockPlatform.MODID,"plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new BERCategory(helper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        IIngredientManager ingredientManager = registration.getIngredientManager();
        BPRecipes recipes = new BPRecipes(ingredientManager);
        registration.addRecipes(BERCategory.TYPE, this.getRecipes(BERecipe.BLOCK_EXCHANGE.get()));
        registration.addIngredientInfo(new ItemStack(BPItems.OBSIDIAN_WRENCH.get()), VanillaTypes.ITEM_STACK, Component.translatable("item.bedrock_platform.obsidian_wrench.des"));
    }

    private <C extends RecipeInput, T extends Recipe<C>> List<T> getRecipes(RecipeType<T> type)
    {
        return getRecipeManager().getAllRecipesFor(type).stream().map(RecipeHolder::value).toList();
    }

    public static RecipeManager getRecipeManager()
    {
        ClientPacketListener listener = Objects.requireNonNull(Minecraft.getInstance().getConnection());
        return listener.getRecipeManager();
    }
}
