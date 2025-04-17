package dev.hail.bedrock_platform.Datagen;

import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.Blocks.BPBlocks;
import dev.hail.bedrock_platform.Blocks.Light.Amethyst.TorchBlockSet;
import dev.hail.bedrock_platform.Blocks.Light.SolidTorchBlock;
import dev.hail.bedrock_platform.Blocks.PlatformBlock;
import dev.hail.bedrock_platform.Blocks.PreciseNetherPortal;
import dev.hail.bedrock_platform.Blocks.StrongInteractionBlockSet;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Half;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class BPBlockModelProvider extends BlockStateProvider {
    public BPBlockModelProvider(PackOutput gen, ExistingFileHelper helper) {
        super(gen, BedrockPlatform.MODID, helper);
    }
    @Override
    protected void registerStatesAndModels() {
        for (DeferredBlock<Block> block : DatagenHandler.cubeAllBlockList) {
            genCubeAllBlockWithItem(block);
        }
        for (StrongInteractionBlockSet color : DatagenHandler.colorSIList) {
            genSISet(color);
        }
        genBlockItemWithRotatedModel(BPBlocks.ENCAPSULATED_END_PORTAL_FRAME);
        genBlockItemWithSpecialModel(BPBlocks.SCULK_RIB_BLOCK);
        genKelpBlockWithItem(BPBlocks.KELP_BLOCK);
        genBlockItemWithSpecialModel(BPBlocks.PERMANENTLY_WETTED_FARMLAND);
        genBlockItemWithSpecialModel(BPBlocks.GLOW_PERMANENTLY_WETTED_FARMLAND);
        genTorchBlock(BPBlocks.STONE_TORCH);
        genTorchBlock(BPBlocks.DEEPSLATE_TORCH);
        genTorchBlockNoVariant(BPBlocks.AMETHYST_CANDLE);
        genAmethystLanternBlockItem(BPBlocks.AMETHYST_LANTERN.getUnwaxed(),"copper_grate");
        genAmethystLanternBlockItem(BPBlocks.EXPOSED_AMETHYST_LANTERN.getUnwaxed(),"exposed_copper_grate");
        genAmethystLanternBlockItem(BPBlocks.WEATHERED_AMETHYST_LANTERN.getUnwaxed(),"weathered_copper_grate");
        genAmethystLanternBlockItem(BPBlocks.OXIDIZED_AMETHYST_LANTERN.getUnwaxed(),"oxidized_copper_grate");
        genAmethystLanternBlockItem(BPBlocks.AMETHYST_LANTERN.getWaxed(),"copper_grate");
        genAmethystLanternBlockItem(BPBlocks.EXPOSED_AMETHYST_LANTERN.getWaxed(),"exposed_copper_grate");
        genAmethystLanternBlockItem(BPBlocks.WEATHERED_AMETHYST_LANTERN.getWaxed(),"weathered_copper_grate");
        genAmethystLanternBlockItem(BPBlocks.OXIDIZED_AMETHYST_LANTERN.getWaxed(),"oxidized_copper_grate");
        genPlatformBlock(BPBlocks.STONE_PLATFORM);
        genPlatformBlockTransparent(BPBlocks.TRANSPARENT_STONE_PLATFORM);
        genPortalDoorBlock(BPBlocks.PRECISE_NETHER_PORTAL);
    }
    protected void genCubeAllBlockWithItem(DeferredBlock<Block> block){
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }
    protected void genTransparentBlockWithItem(DeferredBlock<Block> block){
        simpleBlockWithItem(block.get(), models().cubeAll(getBlockId(block), blockTexture(block.get())).renderType("translucent"));
    }
    protected void genBlockItemWithSpecialModel(DeferredBlock<Block> block){
        simpleBlockWithItem(block.get(), getBlockModel(block));
    }
    protected void genBlockItemWithRotatedModel(DeferredBlock<Block> block){
        horizontalBlock(block.get(), getBlockModel(block),0);
        simpleBlockItem(block.get(), getBlockModel(block));
    }
    protected void genKelpBlockWithItem(DeferredBlock<Block> block){
        simpleBlockWithItem(block.get(), models().cubeColumn(block.getId().getPath(),
                getBlockTexture(getBlockId(block) + "_side"), getBlockTexture(getBlockId(block) + "_end")));
    }
    protected void genTorchBlock(TorchBlockSet torch){
        BlockModelBuilder torchModel = models().withExistingParent(torch.getStand().getId().getPath(),
                ResourceLocation.withDefaultNamespace("block/template_torch"))
                .texture("torch", getBlockTexture(torch.getStand().getId().getPath()))
                .renderType("cutout");
        BlockModelBuilder torchOffModel = models().getBuilder(BuiltInRegistries.BLOCK.getKey(torch.getStand().get()).getPath() + "_off")
                .parent(torchModel)
                .texture("torch", getBlockTexture(torch.getStand().getId().getPath() + "_off")).renderType("cutout");
        getVariantBuilder(torch.getStand().get())
                .partialState().with(SolidTorchBlock.WATERLOGGED, false).modelForState().modelFile(torchModel).addModel()
                .partialState().with(SolidTorchBlock.WATERLOGGED, true).modelForState().modelFile(torchOffModel).addModel();

        BlockModelBuilder wallTorchModel = models().withExistingParent(torch.getWall().getId().getPath(),
                        ResourceLocation.withDefaultNamespace("block/template_torch_wall"))
                .texture("torch", getBlockTexture(torch.getStand().getId().getPath()))
                .renderType("cutout");
        BlockModelBuilder wallTorchOffModel = models().getBuilder(BuiltInRegistries.BLOCK.getKey(torch.getWall().get()).getPath() + "_off")
                .parent(wallTorchModel)
                .texture("torch", getBlockTexture(torch.getStand().getId().getPath() + "_off")).renderType("cutout");
        getVariantBuilder(torch.getWall().get())
                .forAllStates(state -> {
                    Direction facing = state.getValue(SolidTorchBlock.SolidWallTorchBlock.FACING);
                    Boolean waterlogged = state.getValue(SolidTorchBlock.WATERLOGGED);
                    int yRot = (int) facing.getClockWise().toYRot();
                    yRot %= 360;
                    return ConfiguredModel.builder()
                            .modelFile(waterlogged ? wallTorchOffModel : wallTorchModel)
                            .rotationY(yRot)
                            .build();
                });
    }
    protected void genTorchBlockNoVariant(TorchBlockSet torch){
        BlockModelBuilder torchModel = models().withExistingParent(torch.getStand().getId().getPath(),
                        ResourceLocation.withDefaultNamespace("block/template_torch"))
                .texture("torch", getBlockTexture(torch.getStand().getId().getPath()))
                .renderType("cutout");
        models().getBuilder(BuiltInRegistries.BLOCK.getKey(torch.getStand().get()).getPath() + "_off")
                .parent(torchModel)
                .texture("torch", getBlockTexture(torch.getStand().getId().getPath() + "_off")).renderType("cutout");

        BlockModelBuilder wallTorchModel = models().withExistingParent(torch.getWall().getId().getPath(),
                        ResourceLocation.withDefaultNamespace("block/template_torch_wall"))
                .texture("torch", getBlockTexture(torch.getStand().getId().getPath()))
                .renderType("cutout");
        models().getBuilder(BuiltInRegistries.BLOCK.getKey(torch.getWall().get()).getPath() + "_off")
                .parent(wallTorchModel)
                .texture("torch", getBlockTexture(torch.getStand().getId().getPath() + "_off")).renderType("cutout");
    }
    protected void genAmethystLanternBlockItem(DeferredBlock<Block> block, String outer){
        BlockModelBuilder amethystLantern = models().withExistingParent(block.getId().getPath(),
                BedrockPlatform.modResLocation("block/template_amethyst_lantern"));
        simpleBlockItem(block.get(), amethystLantern
                .texture("outer", ResourceLocation.withDefaultNamespace("block/" + outer))
                .texture("torch", BedrockPlatform.modResLocation("block/amethyst_candle")).renderType("cutout"));
        models().getBuilder(BuiltInRegistries.BLOCK.getKey(block.get()).getPath() + "_off").parent(amethystLantern)
                .texture("torch", BedrockPlatform.modResLocation("block/amethyst_candle_off")).renderType("cutout");
    }
    protected void genSISet(StrongInteractionBlockSet color){
        genCubeAllBlockWithItem(color.getBaseBlock());
        genCubeAllBlockWithItem(color.getTile());
        genCubeAllBlockWithItem(color.getSlick());
        genCubeAllBlockWithItem(color.getGlow());
        genCubeAllBlockWithItem(color.getTwill());
        genTransparentBlockWithItem(color.getTransparent());
    }
    protected void genPlatformBlock(DeferredBlock<Block> block){
        BlockModelBuilder platformTop = models().withExistingParent(block.getId().getPath(),
                BedrockPlatform.modResLocation("block/template_platform"));
        simpleBlockItem(block.get(), platformTop
                .texture("all", getBlockTexture(block.getId().getPath())));
        BlockModelBuilder platformBottom = models().withExistingParent(block.getId().getPath() + "_stair",
                BedrockPlatform.modResLocation("block/template_stair_platform"))
                .texture("all", getBlockTexture(block.getId().getPath()));
        getVariantBuilder(block.get())
                .forAllStatesExcept(state -> {
                    Direction facing = state.getValue(PlatformBlock.FACING);
                    Half half = state.getValue(PlatformBlock.HALF);
                    int yRot = (int) facing.getClockWise().toYRot() - 90;
                    yRot %= 360;
                    boolean uvlock = yRot != 0 || half == Half.TOP;
                    return ConfiguredModel.builder()
                            .modelFile(half == Half.TOP ? platformTop : platformBottom)
                            .rotationY(half == Half.TOP ? 0 : yRot)
                            .uvLock(uvlock)
                            .build();
                }, PlatformBlock.WATERLOGGED);
    }
    protected void genPlatformBlockTransparent(DeferredBlock<Block> block){
        BlockModelBuilder platformTop = models().withExistingParent(block.getId().getPath(),
                BedrockPlatform.modResLocation("block/template_platform")).renderType("cutout");
        simpleBlockItem(block.get(), platformTop
                .texture("all", getBlockTexture(block.getId().getPath())));
        BlockModelBuilder platformBottom = models().withExistingParent(block.getId().getPath() + "_stair",
                        BedrockPlatform.modResLocation("block/template_stair_platform")).renderType("cutout")
                .texture("all", getBlockTexture(block.getId().getPath()));
        getVariantBuilder(block.get())
                .forAllStatesExcept(state -> {
                    Direction facing = state.getValue(PlatformBlock.FACING);
                    Half half = state.getValue(PlatformBlock.HALF);
                    int yRot = (int) facing.getClockWise().toYRot() - 90;
                    yRot %= 360;
                    boolean uvlock = yRot != 0 || half == Half.TOP;
                    return ConfiguredModel.builder()
                            .modelFile(half == Half.TOP ? platformTop : platformBottom)
                            .rotationY(half == Half.TOP ? 0 : yRot)
                            .uvLock(uvlock)
                            .build();
                }, PlatformBlock.WATERLOGGED);
    }
    protected void genPortalDoorBlock(DeferredBlock<Block> block){
        getVariantBuilder(block.get())
                .forAllStatesExcept(state -> {
                    Direction.Axis axis = state.getValue(PreciseNetherPortal.AXIS);
                    DoubleBlockHalf half = state.getValue(PreciseNetherPortal.HALF);
                    return ConfiguredModel.builder()
                            .modelFile(half == DoubleBlockHalf.UPPER ?
                                    models().getExistingFile(BedrockPlatform.modResLocation("block/" + block.getId().getPath()  + "_upper")) :
                                    models().getExistingFile(BedrockPlatform.modResLocation("block/" + block.getId().getPath()  + "_lower")))
                            .rotationY(axis == Direction.Axis.X ? 0 : 90)
                            .build();
                }, PreciseNetherPortal.WATERLOGGED);
    }

    protected ModelFile getBlockModel(DeferredBlock<Block> block){
        return new ModelFile(blockTexture(block.get())) {
            @Override protected boolean exists() {return true;}
        };
    }
    protected String getBlockId(DeferredBlock<Block> block){
        return block.getId().getPath();
    }
    protected ResourceLocation getBlockTexture(String blockId){
        return BedrockPlatform.modResLocation("block/" + blockId);
    }
}