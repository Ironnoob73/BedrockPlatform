package dev.hail.bedrock_platform.Blocks;

import dev.hail.bedrock_platform.Blocks.Light.Amethyst.AmethystCandleBlock;
import dev.hail.bedrock_platform.Blocks.Light.Amethyst.AmethystCandleLogic;
import dev.hail.bedrock_platform.Blocks.Light.Amethyst.TorchBlockSet;
import dev.hail.bedrock_platform.Blocks.Light.Amethyst.WaxedAmethystLanternBlockSet;
import dev.hail.bedrock_platform.Blocks.Light.SolidTorchBlock;
import dev.hail.bedrock_platform.Blocks.SolidEnd.SolidEndVoid;
import dev.hail.bedrock_platform.Blocks.SolidEnd.SolidEndVoidBE;
import dev.hail.bedrock_platform.Items.BPItems;
import dev.hail.bedrock_platform.Items.PlatformItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static dev.hail.bedrock_platform.BedrockPlatform.MODID;

public class BPBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    static BlockBehaviour.StatePredicate block_never = (pState, pLevel, pPos) -> false;
    public static BlockBehaviour.Properties bedrockLike(){
        return BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(-1.0F, 3600000.0F)
                    .isValidSpawn(Blocks::never);
    }

    public static final DeferredBlock<Block> BEDROCK_PLATFORM = registerWithItem("bedrock_platform",
            bedrockLike(), new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant());
    public static final DeferredBlock<Block> LUMINOUS_BEDROCK_PLATFORM = registerWithItem("luminous_bedrock_platform",
            bedrockLike().mapColor(MapColor.QUARTZ).lightLevel(l->15),
            new Item.Properties().rarity(Rarity.RARE).fireResistant());
    public static final DeferredBlock<Block> TWILL_BEDROCK_PLATFORM = registerWithItem("twill_bedrock_platform",
            bedrockLike().mapColor(MapColor.TERRACOTTA_YELLOW),
            new Item.Properties().rarity(Rarity.RARE).fireResistant());

    public static final DeferredBlock<Block> SOLID_END_VOID = registerWithItem("solid_end_void",
            ()->new SolidEndVoid(bedrockLike().mapColor(MapColor.COLOR_BLACK).lightLevel(l->15)),
            new Item.Properties().rarity(Rarity.EPIC));
    public static final Supplier<BlockEntityType<SolidEndVoidBE>> SOLID_END_VOID_BE = BLOCK_ENTITY_TYPES.register(
            "solid_end_void_entity",
            () -> BlockEntityType.Builder.of(SolidEndVoidBE::new, SOLID_END_VOID.get()).build(null));

    public static final DeferredBlock<Block> GHAST_TEAR_GLASS = registerWithItem("ghast_tear_glass",()->new TransparentBlock(
            BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)
                    .instrument(NoteBlockInstrument.HAT)
                    .mapColor(MapColor.COLOR_GRAY)
                    .noOcclusion()
                    .lightLevel(l->7)
                    .isValidSpawn(Blocks::never)
                    .isRedstoneConductor(block_never)
                    .isSuffocating(block_never)
                    .isViewBlocking(block_never)));
    public static final DeferredBlock<Block> ENCAPSULATED_END_PORTAL_FRAME = registerWithRareItem("encapsulated_end_portal_frame",
            ()->new EncapsulatedEndPortalFrame(bedrockLike()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .sound(SoundType.GLASS).noOcclusion()));
    public static final DeferredBlock<Block> SCULK_RIB_BLOCK = registerWithItem("sculk_rib_block",()->new SculkRibBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(55.0F, 1200.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.BONE_BLOCK)
                    .isValidSpawn(Blocks::never)));

    public static final StrongInteractionBlockSet RED_SI_BLOCK_SET = StrongInteractionBlockSet
            .builder("red",MapColor.TERRACOTTA_RED).defaultSet().build();
    public static final StrongInteractionBlockSet ORANGE_SI_BLOCK_SET = StrongInteractionBlockSet
            .builder("orange",MapColor.TERRACOTTA_ORANGE).defaultSet().build();
    public static final StrongInteractionBlockSet YELLOW_SI_BLOCK_SET = StrongInteractionBlockSet
            .builder("yellow",MapColor.TERRACOTTA_YELLOW).defaultSet().build();
    public static final StrongInteractionBlockSet GREEN_SI_BLOCK_SET = StrongInteractionBlockSet
            .builder("green",MapColor.TERRACOTTA_GREEN).defaultSet().build();
    public static final StrongInteractionBlockSet CYAN_SI_BLOCK_SET = StrongInteractionBlockSet
            .builder("cyan",MapColor.TERRACOTTA_CYAN).defaultSet().build();
    public static final StrongInteractionBlockSet BLUE_SI_BLOCK_SET = StrongInteractionBlockSet
            .builder("blue",MapColor.TERRACOTTA_BLUE).defaultSet().build();
    public static final StrongInteractionBlockSet PURPLE_SI_BLOCK_SET = StrongInteractionBlockSet
            .builder("purple",MapColor.TERRACOTTA_PURPLE).defaultSet().build();
    public static final StrongInteractionBlockSet WHITE_SI_BLOCK_SET = StrongInteractionBlockSet
            .builder("white",MapColor.TERRACOTTA_WHITE).defaultSet().build();
    public static final StrongInteractionBlockSet GRAY_SI_BLOCK_SET = StrongInteractionBlockSet
            .builder("gray",MapColor.TERRACOTTA_GRAY).defaultSet().build();
    public static final StrongInteractionBlockSet BLACK_SI_BLOCK_SET = StrongInteractionBlockSet
            .builder("black",MapColor.TERRACOTTA_BLACK).defaultSet().build();

    public static final DeferredBlock<Block> GEODE_MOSAIC_TILE = registerWithItem("geode_mosaic_tile",
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .isValidSpawn(Blocks::never)
                    .strength(25.0F, 100.0F)
                    .sound(SoundType.DEEPSLATE));
    public static final DeferredBlock<Block> KELP_BLOCK = registerWithItem("kelp_block",
            ()->new KelpBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIED_KELP_BLOCK)));
    public static final DeferredBlock<Block> PERMANENTLY_WETTED_FARMLAND = registerWithItem("permanently_wetted_farmland",
            ()->new PermanentlyWettedFarmlandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND)));
    public static final DeferredBlock<Block> GLOW_PERMANENTLY_WETTED_FARMLAND = registerWithItem("glow_permanently_wetted_farmland",
            ()->new PermanentlyWettedFarmlandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND).lightLevel(l->8)));

    public static final TorchBlockSet STONE_TORCH = TorchBlockSet
            .builder("stone", false,
                    ()->new SolidTorchBlock(
                            BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel(p -> SeaPickleBlock.isDead(p) ? 15 : 0)
                                    .sound(SoundType.STONE),false
                    ),
                    ()->new SolidTorchBlock.SolidWallTorchBlock(
                            BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel(p -> SeaPickleBlock.isDead(p) ? 15 : 0)
                                    .sound(SoundType.STONE),false
                    )).build();
    public static final TorchBlockSet DEEPSLATE_TORCH = TorchBlockSet
            .builder("deepslate", false,
                    ()->new SolidTorchBlock(
                            BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel(p -> SeaPickleBlock.isDead(p) ? 15 : 0)
                                    .sound(SoundType.DEEPSLATE),true
                    ),
                    ()->new SolidTorchBlock.SolidWallTorchBlock(
                            BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel(p -> SeaPickleBlock.isDead(p) ? 15 : 0)
                                    .sound(SoundType.DEEPSLATE),true
                    )).build();
    public static final TorchBlockSet AMETHYST_CANDLE = TorchBlockSet
            .builder("amethyst", true,
                    ()->new AmethystCandleBlock(
                            ParticleTypes.GLOW,
                            BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel(AmethystCandleLogic::getLight)
                                    .sound(SoundType.AMETHYST)
                    ),
                    ()->new AmethystCandleBlock.AmethystWallCandleBlock(
                            ParticleTypes.GLOW,
                            BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel(AmethystCandleLogic::getLight)
                                    .sound(SoundType.AMETHYST)
                    )).build();
    public static final WaxedAmethystLanternBlockSet AMETHYST_LANTERN = WaxedAmethystLanternBlockSet
            .builder("",WeatheringCopper.WeatherState.UNAFFECTED,Blocks.COPPER_GRATE).build();
    public static final WaxedAmethystLanternBlockSet EXPOSED_AMETHYST_LANTERN = WaxedAmethystLanternBlockSet
            .builder("exposed_",WeatheringCopper.WeatherState.EXPOSED,Blocks.EXPOSED_COPPER_GRATE).build();
    public static final WaxedAmethystLanternBlockSet WEATHERED_AMETHYST_LANTERN = WaxedAmethystLanternBlockSet
            .builder("weathered_",WeatheringCopper.WeatherState.WEATHERED,Blocks.WEATHERED_COPPER_GRATE).build();
    public static final WaxedAmethystLanternBlockSet OXIDIZED_AMETHYST_LANTERN = WaxedAmethystLanternBlockSet
            .builder("oxidized_",WeatheringCopper.WeatherState.OXIDIZED,Blocks.OXIDIZED_COPPER_GRATE).build();

    public static final DeferredBlock<Block> STONE_PLATFORM = registerWithPlatItem("stone_platform",
            ()-> new PlatformBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_STONE_BRICKS)
                    .noCollission()
                    .dynamicShape()
                    .isRedstoneConductor(block_never)));
    public static final DeferredBlock<Block> TRANSPARENT_STONE_PLATFORM = registerWithPlatItem("transparent_stone_platform",
            ()-> new PlatformBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_STONE_BRICKS)
                    .noCollission()
                    .dynamicShape()
                    .isRedstoneConductor(block_never)));

    public static final DeferredBlock<Block> PRECISE_NETHER_PORTAL = registerWithItem("precise_nether_portal",
            ()->new PreciseNetherPortal(BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
                    .noCollission()
                    .sound(SoundType.METAL)
                    .lightLevel(p -> 11)
                    .pushReaction(PushReaction.BLOCK)));

    public static <B extends Block> DeferredBlock<B> registerWithItem(String id, Supplier<B> block) {
        DeferredBlock<B> object = BLOCKS.register(id, block);
        BPItems.ITEMS.registerSimpleBlockItem(object);
        return object;
    }
    public static DeferredBlock<Block> registerWithItem(String id, BlockBehaviour.Properties block) {
        DeferredBlock<Block> object = BLOCKS.register(id, () -> new Block(block));
        BPItems.ITEMS.registerSimpleBlockItem(object);
        return object;
    }
    public static <B extends Block> DeferredBlock<B> registerWithItem(String id, Supplier<B> block, Item.Properties item) {
        DeferredBlock<B> object = BLOCKS.register(id, block);
        BPItems.ITEMS.registerSimpleBlockItem(object, item);
        return object;
    }
    public static <B extends Block> DeferredBlock<B> registerWithPlatItem(String id, Supplier<B> block) {
        DeferredBlock<B> object = BLOCKS.register(id, block);
        BPItems.ITEMS.register(id, () -> new PlatformItem(object.get(), new Item.Properties()));
        return object;
    }
    public static DeferredBlock<Block> registerWithItem(String id, BlockBehaviour.Properties block, Item.Properties item) {
        DeferredBlock<Block> object = BLOCKS.register(id, () -> new Block(block));
        BPItems.ITEMS.registerSimpleBlockItem(object, item);
        return object;
    }
    public static <B extends Block> DeferredBlock<B> registerWithRareItem(String id, Supplier<B> block) {
        DeferredBlock<B> object = BLOCKS.register(id, block);
        BPItems.ITEMS.registerSimpleBlockItem(object, new Item.Properties().rarity(Rarity.RARE));
        return object;
    }
    public static <B extends Block> DeferredBlock<B> registerWithUncommonItem(String id, Supplier<B> block) {
        DeferredBlock<B> object = BLOCKS.register(id, block);
        BPItems.ITEMS.registerSimpleBlockItem(object, new Item.Properties().rarity(Rarity.UNCOMMON));
        return object;
    }
}
