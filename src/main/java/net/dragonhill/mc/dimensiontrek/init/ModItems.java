package net.dragonhill.mc.dimensiontrek.init;

import net.dragonhill.mc.dimensiontrek.config.Constants;
import net.dragonhill.mc.dimensiontrek.items.biomeanalyzer.BiomeAnalyzerItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.modId);

    public static RegistryObject<BiomeAnalyzerItem> biomeAnalyzer = items.register("biome_analyzer", () -> new BiomeAnalyzerItem());
}
