package dev.hail.bedrock_platform.recipe.block_exchange;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.hail.bedrock_platform.BedrockPlatform;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BERSerializer implements RecipeSerializer<BERecipe> {
    public static final MapCodec<BERecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            BlockState.CODEC.fieldOf("state").forGetter(BERecipe::inputState),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(BERecipe::inputItem),
            BlockState.CODEC.fieldOf("result_state").forGetter(BERecipe::resultState)
    ).apply(inst, BERecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BERecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY), BERecipe::inputState,
                    Ingredient.CONTENTS_STREAM_CODEC, BERecipe::inputItem,
                    ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY), BERecipe::resultState,
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
