package net.dragonhill.mc.dimensiontrek;

import net.dragonhill.mc.dimensiontrek.config.Config;
import net.dragonhill.mc.dimensiontrek.config.Constants;
import net.dragonhill.mc.dimensiontrek.init.ModContainers;
import net.dragonhill.mc.dimensiontrek.init.ModItems;
import net.dragonhill.mc.dimensiontrek.init.ModPackets;
import net.dragonhill.mc.dimensiontrek.init.ModScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.modId)
@Mod.EventBusSubscriber(modid = Constants.modId, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DimensionTrek {
    public DimensionTrek() {
        Config.register(ModLoadingContext.get());


        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModItems.items.register(modEventBus);
        ModContainers.containers.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent setupEvent) {
        ModPackets.registerPackets();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ModScreens.registerScreens();
    }
}
