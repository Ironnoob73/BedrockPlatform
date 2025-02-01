package dev.hail.bedrock_platform.BlockReductionRecipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.BlockExchangeRecipe.BERecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BRRSerializer implements RecipeSerializer<BRRecipe> {
    public static final MapCodec<BRRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            BlockState.CODEC.fieldOf("state").forGetter(BRRecipe::getInputState),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(BRRecipe::getInputItem),
            ItemStack.CODEC.fieldOf("result").forGetter(BRRecipe::getResult),
            BlockState.CODEC.fieldOf("result_state").forGetter(BRRecipe::getResultState)
    ).apply(inst, BRRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BRRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY), BRRecipe::getInputState,
                    Ingredient.CONTENTS_STREAM_CODEC, BRRecipe::getInputItem,
                    ItemStack.STREAM_CODEC, BRRecipe::getResult,
                    ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY), BRRecipe::getResultState,
                    BRRecipe::new
            );

    // Return our map codec.
    @Override
    public MapCodec<BRRecipe> codec() {
        return CODEC;
    }

    // Return our stream codec.
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, BRRecipe> streamCodec() {
        return STREAM_CODEC;
    }
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, BedrockPlatform.MODID);

    public static final Supplier<RecipeSerializer<BRRecipe>> BLOCK_REDUCTION =
            RECIPE_SERIALIZERS.register("block_reduction", BRRSerializer::new);
}
