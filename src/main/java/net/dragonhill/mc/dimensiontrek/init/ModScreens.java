package net.dragonhill.mc.dimensiontrek.init;

import net.dragonhill.mc.dimensiontrek.items.biomeanalyzer.BiomeAnalyzerScreen;
import net.minecraft.client.gui.ScreenManager;

public class ModScreens {
    public static void registerScreens() {
        ScreenManager.register(ModContainers.biomeAnalyzerContainer.get(), BiomeAnalyzerScreen::new);
    }
}
