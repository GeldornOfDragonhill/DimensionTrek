package net.dragonhill.mc.dimensiontrek;

import net.dragonhill.mc.dimensiontrek.config.Constants;
import net.dragonhill.mc.dimensiontrek.init.ModContainers;
import net.dragonhill.mc.dimensiontrek.init.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.modId)
public class DimensionTrek {
    public DimensionTrek() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.items.register(modEventBus);
        ModContainers.containers.register(modEventBus);
    }
}
