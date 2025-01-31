package dev.hail.bedrock_platform.BlockExchangeRecipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.hail.bedrock_platform.BedrockPlatform;
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

public class BERSerializer implements RecipeSerializer<BERecipe> {
    public static final MapCodec<BERecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            BlockState.CODEC.fieldOf("state").forGetter(BERecipe::getInputState),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(BERecipe::getInputItem),
            ItemStack.CODEC.fieldOf("result").forGetter(BERecipe::getResult),
            BlockState.CODEC.fieldOf("result_state").forGetter(BERecipe::getResultState)
    ).apply(inst, BERecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BERecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY), BERecipe::getInputState,
                    Ingredient.CONTENTS_STREAM_CODEC, BERecipe::getInputItem,
                    ItemStack.STREAM_CODEC, BERecipe::getResult,
                    ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY), BERecipe::getResultState,
                    BERecipe::new
            );

    // Return our map codec.
    @Override
    public MapCodec<BERecipe> codec() {
        return CODEC;
    }

    // Return our stream codec.
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, BERecipe> streamCodec() {
        return STREAM_CODEC;
    }
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, BedrockPlatform.MODID);

    public static final Supplier<RecipeSerializer<BERecipe>> BLOCK_EXCHANGE =
            RECIPE_SERIALIZERS.register("block_exchange", BERSerializer::new);
}
