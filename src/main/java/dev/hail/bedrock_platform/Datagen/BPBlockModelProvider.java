package dev.hail.bedrock_platform.Datagen;

import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.Blocks.*;
import dev.hail.bedrock_platform.Blocks.Light.Amethyst.TorchBlockSet;
import dev.hail.bedrock_platform.Blocks.Light.SolidTorchBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
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
        genCutoutBlockWithItem(BPBlocks.GHAST_TEAR_GLASS);
        genBlockItemWithRotatedModel(BPBlocks.ENCAPSULATED_END_PORTAL_FRAME);
        genBlockItemWithSpecialModel(BPBlocks.SCULK_RIB_BLOCK);
        simpleBlockWithItem(BPBlocks.FILLED_SCULK_RIB_BLOCK.get(), models().cubeBottomTop(BPBlocks.FILLED_SCULK_RIB_BLOCK.getId().getPath(),
                getBlockTexture(getBlockId(BPBlocks.SCULK_RIB_BLOCK) + "_side"),
                getBlockTexture(getBlockId(BPBlocks.FILLED_SCULK_RIB_BLOCK) + "_bottom"),
                getBlockTexture(getBlockId(BPBlocks.FILLED_SCULK_RIB_BLOCK) + "_top")));
        genColumnBlockWithItem(BPBlocks.KELP_BLOCK);
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
        genPlatformBlock(BPBlocks.OAK_PLATFORM);
        genPlatformBlockTransparent(BPBlocks.TRANSPARENT_OAK_PLATFORM);
        genPlatformBlock(BPBlocks.BIRCH_PLATFORM);
        genPlatformBlockTransparent(BPBlocks.TRANSPARENT_BIRCH_PLATFORM);
        genPlatformBlock(BPBlocks.SPRUCE_PLATFORM);
        genPlatformBlockTransparent(BPBlocks.TRANSPARENT_SPRUCE_PLATFORM);
        genPlatformBlock(BPBlocks.JUNGLE_PLATFORM);
        genPlatformBlockTransparent(BPBlocks.TRANSPARENT_JUNGLE_PLATFORM);
        genPlatformBlock(BPBlocks.DARK_OAK_PLATFORM);
        genPlatformBlockTransparent(BPBlocks.TRANSPARENT_DARK_OAK_PLATFORM);
        genPlatformBlock(BPBlocks.ACACIA_PLATFORM);
        genPlatformBlockTransparent(BPBlocks.TRANSPARENT_ACACIA_PLATFORM);
        genPlatformBlock(BPBlocks.MANGROVE_PLATFORM);
        genPlatformBlockTransparent(BPBlocks.TRANSPARENT_MANGROVE_PLATFORM);
        genPlatformBlock(BPBlocks.CHERRY_PLATFORM);
        genPlatformBlockTransparent(BPBlocks.TRANSPARENT_CHERRY_PLATFORM);
        genPlatformBlock(BPBlocks.STONE_PLATFORM);
        genPlatformBlockTransparent(BPBlocks.TRANSPARENT_STONE_PLATFORM);
        genPlatformBlock(BPBlocks.CRIMSON_PLATFORM);
        genPlatformBlockTransparent(BPBlocks.TRANSPARENT_CRIMSON_PLATFORM);
        genPlatformBlock(BPBlocks.WARPED_PLATFORM);
        genPlatformBlockTransparent(BPBlocks.TRANSPARENT_WARPED_PLATFORM);
        genPlatformBlockTransparent(BPBlocks.GLASS_PLATFORM);
        genPortalDoorBlock(BPBlocks.PRECISE_NETHER_PORTAL);
        genDVSet(BPBlocks.GEODE_MOSAIC_TILE);
        genDVSet(BPBlocks.GEODE_WHITE_TILES);
        genDVSet(BPBlocks.GEODE_WHITE_SMOOTH_TILE);
        genDVSet(BPBlocks.GEODE_WHITE_BRICKS);
        genAxisBlockWithItem(BPBlocks.GEODE_WHITE_PILLAR);
        genColumnBlockWithItem(BPBlocks.GEODE_WHITE_CRATE);
        genDVSet(BPBlocks.GEODE_BLACK_TILES);
        genDVSet(BPBlocks.GEODE_BLACK_SMOOTH_TILE);
        genDVSet(BPBlocks.GEODE_BLACK_BRICKS);
        genAxisBlockWithItem(BPBlocks.GEODE_BLACK_PILLAR);
        genColumnBlockWithItem(BPBlocks.GEODE_BLACK_CRATE);
        genDVSet(BPBlocks.GEODE_GRAY_TILES);
        genDVSet(BPBlocks.GEODE_GRAY_SMOOTH_TILE);
        genDVSet(BPBlocks.GEODE_GRAY_BRICKS);
        genAxisBlockWithItem(BPBlocks.GEODE_GRAY_PILLAR);
        genColumnBlockWithItem(BPBlocks.GEODE_GRAY_CRATE);
        genDVSet(BPBlocks.GEODE_BLUE_TILES);
        genDVSet(BPBlocks.GEODE_BLUE_SMOOTH_TILE);
        genDVSet(BPBlocks.GEODE_BLUE_BRICKS);
        genAxisBlockWithItem(BPBlocks.GEODE_BLUE_PILLAR);
        genColumnBlockWithItem(BPBlocks.GEODE_BLUE_CRATE);
        genDVSet(BPBlocks.GEODE_GRAY_WHITE_TILES);
        genDVSet(BPBlocks.GEODE_BLUE_WHITE_TILES);
        genTransparentBlockWithItem(BPBlocks.GEODE_TINTED_WHITE_GLASS);
        genTransparentBlockWithItem(BPBlocks.GEODE_TINTED_BLACK_GLASS);
        genTransparentBlockWithItem(BPBlocks.GEODE_TINTED_GRAY_GLASS);
        genTransparentBlockWithItem(BPBlocks.GEODE_TINTED_BLUE_GLASS);
        genPadBlock(BPBlocks.BOUNCE_PAD);
    }
    protected void genCubeAllBlockWithItem(DeferredBlock<Block> block){
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }
    protected void genTransparentBlockWithItem(DeferredBlock<Block> block){
        simpleBlockWithItem(block.get(), models().cubeAll(getBlockId(block), blockTexture(block.get())).renderType("translucent"));
    }
    protected void genCutoutBlockWithItem(DeferredBlock<Block> block){
        simpleBlockWithItem(block.get(), models().cubeAll(getBlockId(block), blockTexture(block.get())).renderType("cutout"));
    }
    protected void genStairsBlockWithItem(DeferredBlock<Block> block, DeferredBlock<Block> oBlock){
        stairsBlock((StairBlock) block.get(), getBlockTexture(getBlockId(oBlock)));
        simpleBlockItem(block.get(), models().stairs(block.getId().getPath(),
                getBlockTexture(getBlockId(oBlock)),getBlockTexture(getBlockId(oBlock)),getBlockTexture(getBlockId(oBlock))));
    }
    protected void genSlabBlockWithItem(DeferredBlock<Block> block, DeferredBlock<Block> oBlock){
        slabBlock((SlabBlock) block.get(), getBlockTexture(getBlockId(oBlock)), getBlockTexture(getBlockId(oBlock)));
        simpleBlockItem(block.get(), models().slab(block.getId().getPath(),
                getBlockTexture(getBlockId(oBlock)),getBlockTexture(getBlockId(oBlock)),getBlockTexture(getBlockId(oBlock))));
    }
    protected void genWallBlockWithItem(DeferredBlock<Block> block, DeferredBlock<Block> oBlock){
        wallBlock((WallBlock) block.get(), getBlockTexture(getBlockId(oBlock)));
        simpleBlockItem(block.get(), models().wallInventory(block.getId().getPath(),
                getBlockTexture(getBlockId(oBlock))));
    }
    protected void genBlockItemWithSpecialModel(DeferredBlock<Block> block){
        simpleBlockWithItem(block.get(), getBlockModel(block));
    }
    protected void genBlockItemWithRotatedModel(DeferredBlock<Block> block){
        horizontalBlock(block.get(), getBlockModel(block),0);
        simpleBlockItem(block.get(), getBlockModel(block));
    }
    protected void genColumnBlockWithItem(DeferredBlock<Block> block){
        simpleBlockWithItem(block.get(), models().cubeColumn(block.getId().getPath(),
                getBlockTexture(getBlockId(block) + "_side"), getBlockTexture(getBlockId(block) + "_end")));
    }
    protected void genAxisBlockWithItem(DeferredBlock<Block> block){
        axisBlock((RotatedPillarBlock) block.get());
        simpleBlockItem(block.get(), models().cubeColumn(block.getId().getPath(),
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
    protected void genDVSet(DecoVariantBlockSet block){
        genCubeAllBlockWithItem(block.getBaseBlock());
        genStairsBlockWithItem(block.getStairs(), block.getBaseBlock());
        genSlabBlockWithItem(block.getSlab(), block.getBaseBlock());
        genWallBlockWithItem(block.getWall(), block.getBaseBlock());
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
    protected void genPadBlock(DeferredBlock<Block> block){
        getVariantBuilder(block.get())
                .forAllStatesExcept(state -> {
                    Direction direction = state.getValue(BouncePadBlock.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(models().withExistingParent(block.getId().getPath(),
                                    ResourceLocation.withDefaultNamespace("block/pressure_plate_up"))
                                    .texture("texture",ResourceLocation.withDefaultNamespace("block/yellow_stained_glass"))
                                    .renderType("translucent"))
                            .rotationX(direction == Direction.DOWN ? 180 : direction.getAxis().isHorizontal() ? 90 : 0)
                            .rotationY(direction.getAxis().isVertical() ? 0 : (((int) direction.toYRot()) + 180) % 360)
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