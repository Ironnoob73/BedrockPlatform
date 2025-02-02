package dev.hail.bedrock_platform.Recipe.JEI;

import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.Items.BPItems;
import dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe.BERecipe;
import dev.hail.bedrock_platform.Recipe.BlockReductionRecipe.BRRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.state.BlockState;
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
        registration.addRecipeCategories(new BRRCategory(helper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        registration.addRecipes(BERCategory.TYPE, this.getRecipes(BERecipe.BLOCK_EXCHANGE.get()));
        registration.addRecipes(BRRCategory.TYPE, this.getRecipes(BRRecipe.BLOCK_REDUCTION.get()));
        registration.addIngredientInfo(new ItemStack(BPItems.OBSIDIAN_WRENCH.get()), VanillaTypes.ITEM_STACK, Component.translatable("item.bedrock_platform.obsidian_wrench.des"));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(BPItems.OBSIDIAN_WRENCH.get()), BRRCategory.TYPE);
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
    public static ItemStack getItemStackFromBlockState(BlockState blockState){
        ItemStack itemStack = blockState.getBlock().asItem().getDefaultInstance();
        itemStack.set(DataComponents.LORE,new ItemLore(List.of(Component.nullToEmpty(blockState.toString()))));
        return itemStack;
    }
}
