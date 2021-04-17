package net.dragonhill.mc.dimensiontrek.items.biomeanalyzer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.dragonhill.mc.dimensiontrek.config.Constants;
import net.dragonhill.mc.dimensiontrek.ui.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BiomeAnalyzerScreen extends InventoryScreen<BiomeAnalyzerContainer> {

    public static final int USED_CLIENT_WIDTH = 44;
    public static final int USED_CLIENT_HEIGHT = 66;
    public static final int CLIENT_HEIGHT = USED_CLIENT_HEIGHT + 2 * 10;

    private static final ResourceLocation BIOME_ANALYZER_TEXTURE = new ResourceLocation(Constants.modId, "textures/gui/biome_analyzer.png");

    public BiomeAnalyzerScreen(BiomeAnalyzerContainer screenContainer, PlayerInventory playerInventory, ITextComponent title) {
        super(screenContainer, playerInventory, title);
    }

    @Override
    protected void drawClientBackground(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {

        this.minecraft.getTextureManager().bind(BIOME_ANALYZER_TEXTURE);

        this.blit(matrixStack, this.getEffectiveClientXOffset(), this.getEffectiveClientYOffset(), USED_CLIENT_WIDTH, USED_CLIENT_HEIGHT, 0, 0, USED_CLIENT_WIDTH, USED_CLIENT_HEIGHT, 128, 128);
    }

    @Override
    protected void drawClientForeground(MatrixStack matrixStack, int mouseX, int mouseY) {

    }
}
