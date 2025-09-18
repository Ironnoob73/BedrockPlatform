package dev.hail.bedrock_platform.item;

import dev.hail.bedrock_platform.entity.NetherBoat;
//import net.minecraft.core.component.DataComponents;
//import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShearsItem;
//import net.minecraft.world.item.component.ItemLore;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

import static dev.hail.bedrock_platform.BedrockPlatform.MODID;

public class BPItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> OBSIDIAN_WRENCH = ITEMS.register("obsidian_wrench", ()-> new ObsidianWrench(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)) );
    public static final RegistryObject<Item> SCULK_RIB = registerSimpleItem("sculk_rib", new Item.Properties()/*.component(DataComponents.LORE,new ItemLore(List.of(Component.nullToEmpty("Tastes like fingernails"))))*/);
    public static final RegistryObject<Item> ENCHANT_DUST = registerSimpleItem("enchant_dust");
    public static final RegistryObject<Item> BLUE_ICE_CUBE = registerSimpleItem("blue_ice_cube");
    public static final RegistryObject<Item> CRIMSON_BOAT = ITEMS.register("crimson_boat", ()-> new NetherBoatItem(false, NetherBoat.CRIMSON.getValue("aa").get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CRIMSON_CHEST_BOAT = ITEMS.register("crimson_chest_boat", ()-> new NetherBoatItem(true, NetherBoat.CRIMSON.getValue(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WARPED_BOAT = ITEMS.register("warped_boat", ()-> new NetherBoatItem(false, NetherBoat.WARPED.getValue(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WARPED_CHEST_BOAT = ITEMS.register("warped_chest_boat", ()-> new NetherBoatItem(true, NetherBoat.WARPED.getValue(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SAW_BLADE_COMB = ITEMS.register("saw_blade_comb", ()-> new SawBladeCombItem(new Item.Properties().durability(512)/*.component(DataComponents.TOOL, ShearsItem.createToolProperties())*/));
    public static final RegistryObject<Item> INVALID_DECOMPOSITION_PRODUCTS = ITEMS.register("ineffective_decomposition_products", ()-> new InvalidDecompositionProductsItem(new Item.Properties()));

    public static RegistryObject<Item> registerSimpleItem(String id){
        return ITEMS.register(id, () -> new Item(new Item.Properties()));
    }
    public static RegistryObject<Item> registerSimpleItem(String id, Item.Properties properties){
        return ITEMS.register(id, () -> new Item(properties));
    }
}
