package net.dragonhill.mc.dimensiontrek.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.dragonhill.mc.dimensiontrek.config.Constants;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class InventoryScreen<T extends InventoryContainerBase> extends ContainerScreen<T> {

    protected static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Constants.modId, "textures/gui/generic_ui.png");

    protected final int clientHeight;

    abstract protected void drawClientBackground(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY);
    abstract protected void drawClientForeground(MatrixStack matrixStack, int mouseX, int mouseY);

    protected InventoryScreen(final T screenContainer, final PlayerInventory playerInventory, final ITextComponent title) {
        super(screenContainer, playerInventory, title);

        this.clientHeight = screenContainer.getClientHeight();

        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 86 + this.clientHeight;
    }

    protected int getClientX() {
        return (this.width - this.imageWidth) / 2 + 7;
    }

    protected int getClientY() {
        return (this.height - this.imageHeight) / 2 + 7;
    }

    protected int getClientWidth() {
        return this.imageWidth - 14;
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);

        this.minecraft.getTextureManager().bind(BACKGROUND_TEXTURE);

        //Draw the top border
        //blit(MatrixStack matrixStack, int x, int y, int uOffset, int vOffset, int uWidth, int vHeight)
        this.blit(matrixStack, this.leftPos, this.topPos, 0, 83, this.imageWidth, 3);

        //Draw the client background and side borders
        //blit(MatrixStack matrixStack, int x, int y, int width, int height, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight)
        this.blit(matrixStack, this.leftPos, this.topPos + 3, this.imageWidth, this.clientHeight, 0f, 76f, 176, 1, 256, 256);

        //Draw the bottom part
        this.blit(matrixStack, this.leftPos, this.topPos + this.clientHeight + 3, 0, 0, this.imageWidth, 83);

        this.drawClientBackground(matrixStack, partialTicks, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.font.draw(matrixStack, this.title.getString(), 8f, 5f, 0xFF000000);

        this.drawClientForeground(matrixStack, mouseX, mouseY);
    }
}
