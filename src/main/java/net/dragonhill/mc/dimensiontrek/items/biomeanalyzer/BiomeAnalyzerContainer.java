package net.dragonhill.mc.dimensiontrek.items.biomeanalyzer;

import net.dragonhill.mc.dimensiontrek.init.ModContainers;
import net.dragonhill.mc.dimensiontrek.ui.InventoryContainerBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
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
        return 120;
    }

    @Override
    protected void setupCustomInventory(IInventory inventory) {

    }
}
