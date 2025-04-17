package dev.hail.bedrock_platform.Items;

import dev.hail.bedrock_platform.Entities.NetherBoat.NetherBoat;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemLore;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

import static dev.hail.bedrock_platform.BedrockPlatform.MODID;

public class BPItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredItem<ObsidianWrench> OBSIDIAN_WRENCH = ITEMS.registerItem("obsidian_wrench", ObsidianWrench::new, new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    public static final DeferredItem<Item> SCULK_RIB = ITEMS.registerSimpleItem("sculk_rib", new Item.Properties().component(DataComponents.LORE,new ItemLore(List.of(Component.nullToEmpty("Tastes like fingernails")))));
    public static final DeferredItem<Item> ENCHANT_DUST = ITEMS.registerSimpleItem("enchant_dust");
    public static final DeferredItem<Item> BLUE_ICE_CUBE = ITEMS.registerSimpleItem("blue_ice_cube");
    public static final DeferredItem<Item> CRIMSON_BOAT = ITEMS.register("crimson_boat", ()-> new NetherBoatItem(false, NetherBoat.CRIMSON.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> CRIMSON_CHEST_BOAT = ITEMS.register("crimson_chest_boat", ()-> new NetherBoatItem(true, NetherBoat.CRIMSON.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> WARPED_BOAT = ITEMS.register("warped_boat", ()-> new NetherBoatItem(false, NetherBoat.WARPED.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> WARPED_CHEST_BOAT = ITEMS.register("warped_chest_boat", ()-> new NetherBoatItem(true, NetherBoat.WARPED.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> INVALID_DECOMPOSITION_PRODUCTS = ITEMS.registerItem("ineffective_decomposition_products", InvalidDecompositionProductsItem::new);
}
