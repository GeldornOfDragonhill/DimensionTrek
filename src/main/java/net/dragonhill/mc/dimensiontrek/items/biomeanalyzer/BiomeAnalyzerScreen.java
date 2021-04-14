package net.dragonhill.mc.dimensiontrek.items.biomeanalyzer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonhill.mc.dimensiontrek.ui.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class BiomeAnalyzerScreen extends InventoryScreen<BiomeAnalyzerContainer> {

    public BiomeAnalyzerScreen(BiomeAnalyzerContainer screenContainer, PlayerInventory playerInventory, ITextComponent title) {
        super(screenContainer, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void drawClientBackground(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {

    }

    @Override
    protected void drawClientForeground(MatrixStack matrixStack, int mouseX, int mouseY) {

    }
}
