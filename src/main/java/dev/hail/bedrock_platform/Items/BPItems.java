package dev.hail.bedrock_platform.Items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static dev.hail.bedrock_platform.BedrockPlatform.MODID;

public class BPItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredItem<ObsidianWrench> OBSIDIAN_WRENCH = ITEMS.registerItem("obsidian_wrench", ObsidianWrench::new, new Item.Properties().rarity(Rarity.EPIC));
}
