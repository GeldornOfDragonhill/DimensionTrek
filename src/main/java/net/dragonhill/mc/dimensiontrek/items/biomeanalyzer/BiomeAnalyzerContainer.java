package net.dragonhill.mc.dimensiontrek.items.biomeanalyzer;

import net.dragonhill.mc.dimensiontrek.init.ModContainers;
import net.dragonhill.mc.dimensiontrek.ui.InventoryContainerBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.network.PacketBuffer;

public class BiomeAnalyzerContainer extends InventoryContainerBase {
    public BiomeAnalyzerContainer(final int windowId, final PlayerInventory playerInventory, IInventory customInventory) {
        super(ModContainers.biomeAnalyzerContainer.get(), windowId, playerInventory, customInventory);
    }

    //The client constructor
    public BiomeAnalyzerContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer extraData) {
        this(windowId, playerInventory, getTempClientInventory(BiomeAnalyzerItem.NUM_CUSTOM_SLOTS));
    }

    @Override
    protected int getClientHeight() {
        return BiomeAnalyzerScreen.CLIENT_HEIGHT;
    }

    @Override
    protected int getUsedClientWidth() {
        return BiomeAnalyzerScreen.USED_CLIENT_WIDTH;
    }

    @Override
    protected int getUsedClientHeight() {
        return BiomeAnalyzerScreen.USED_CLIENT_HEIGHT;
    }

    @Override
    protected void setupCustomInventory(IInventory inventory) {
        this.addSlotWithEffectiveOffset(inventory, 0, 27, 1);
        this.addSlotWithEffectiveOffset(inventory, 1, 1, 24);
        this.addSlotWithEffectiveOffset(inventory, 2, 27, 49);
    }
}
