package net.dragonhill.mc.dimensiontrek.init;

import net.dragonhill.mc.dimensiontrek.config.Constants;
import net.dragonhill.mc.dimensiontrek.items.biomeanalyzer.BiomeAnalyzerContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
    public static final DeferredRegister<ContainerType<?>> containers = DeferredRegister.create(ForgeRegistries.CONTAINERS, Constants.modId);

    public static final RegistryObject<ContainerType<BiomeAnalyzerContainer>> biomeAnalyzerContainer = containers.register("biome_analyzer_container", () -> IForgeContainerType.create(BiomeAnalyzerContainer::new));
}
