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
    public static final int TEXTURE_SIZE = 128;
    public static final int CLIENT_U = 0;
    public static final int CLIENT_V = 0;
    public static final int PROGRESS_X = 31;
    public static final int PROGRESS_Y = 20;
    public static final int PROGRESS_OVERLAY_U = 44;
    public static final int PROGRESS_OVERLAY_V = 0;
    public static final int PROGRESS_WIDTH = 8;
    public static final int PROGRESS_HEIGHT = 27;

    private static final ResourceLocation BIOME_ANALYZER_TEXTURE = new ResourceLocation(Constants.modId, "textures/gui/biome_analyzer.png");

    public BiomeAnalyzerScreen(BiomeAnalyzerContainer screenContainer, PlayerInventory playerInventory, ITextComponent title) {
        super(screenContainer, playerInventory, title);
    }

    @Override
    protected void drawClientBackground(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {

        this.minecraft.getTextureManager().bind(BIOME_ANALYZER_TEXTURE);

        //Draw the centered background texture
        this.blit(matrixStack, this.getEffectiveClientXOffset(), this.getEffectiveClientYOffset(), USED_CLIENT_WIDTH, USED_CLIENT_HEIGHT, CLIENT_U, CLIENT_V, USED_CLIENT_WIDTH, USED_CLIENT_HEIGHT, TEXTURE_SIZE, TEXTURE_SIZE);

        //Draw the progress bar
        int progressPixel = (int)(((float)this.menu.getCurrentProgress() / (float)this.menu.getNumTicksPerCycle()) * PROGRESS_HEIGHT);
        this.blit(matrixStack, this.getEffectiveClientXOffset() + PROGRESS_X, this.getEffectiveClientYOffset() + PROGRESS_Y, PROGRESS_WIDTH, progressPixel, PROGRESS_OVERLAY_U, PROGRESS_OVERLAY_V, PROGRESS_WIDTH, progressPixel, TEXTURE_SIZE, TEXTURE_SIZE);
    }

    @Override
    protected void drawClientForeground(MatrixStack matrixStack, int mouseX, int mouseY) {

    }
}
