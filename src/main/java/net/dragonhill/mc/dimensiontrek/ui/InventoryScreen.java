package net.dragonhill.mc.dimensiontrek.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.dragonhill.mc.dimensiontrek.config.Constants;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class InventoryScreen<T extends InventoryContainerBase> extends ContainerScreen<T> {

    public final static int TOP_BORDER_HEIGHT = 3;
    public final static int TITLE_HEIGHT = 10;
    public final static int TOP_PART_HEIGHT = TOP_BORDER_HEIGHT + TITLE_HEIGHT;

    protected static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Constants.modId, "textures/gui/generic_ui.png");

    protected final int clientHeight;
    private int effectiveClientXOffset;
    private int effectiveClientYOffset;

    public int getEffectiveClientXOffset() {
        return effectiveClientXOffset;
    }

    public int getEffectiveClientYOffset() {
        return effectiveClientYOffset;
    }


    abstract protected void drawClientBackground(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY);
    abstract protected void drawClientForeground(MatrixStack matrixStack, int mouseX, int mouseY);

    protected InventoryScreen(final T screenContainer, final PlayerInventory playerInventory, final ITextComponent title) {
        super(screenContainer, playerInventory, title);

        this.clientHeight = screenContainer.getClientHeight();

        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 83 + TOP_BORDER_HEIGHT + TITLE_HEIGHT + this.clientHeight;
    }

    @Override
    protected void init() {
        super.init();

        this.effectiveClientXOffset = (this.width - this.menu.getUsedClientWidth()) / 2;
        this.effectiveClientYOffset = this.topPos + TOP_PART_HEIGHT + (this.clientHeight - this.menu.getUsedClientHeight()) / 2;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
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
        this.blit(matrixStack, this.leftPos, this.topPos + TOP_BORDER_HEIGHT, this.imageWidth, this.clientHeight + TITLE_HEIGHT, 0f, 76f, 176, 1, 256, 256);

        //Draw the bottom part
        this.blit(matrixStack, this.leftPos, this.topPos + this.clientHeight + TOP_BORDER_HEIGHT + TITLE_HEIGHT, 0, 0, this.imageWidth, 83);

        this.drawClientBackground(matrixStack, partialTicks, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.font.draw(matrixStack, this.title.getString(), 8f, 5f, 0xFF000000);

        this.drawClientForeground(matrixStack, mouseX, mouseY);
    }
}
