package net.dragonhill.mc.dimensiontrek.items.biomeanalyzer;

import net.dragonhill.mc.dimensiontrek.DimensionTrekException;
import net.dragonhill.mc.dimensiontrek.capabilities.OutputModifiableItemStackHandler;
import net.dragonhill.mc.dimensiontrek.init.ModItems;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class BiomeAnalyzerItemStackHandler extends OutputModifiableItemStackHandler {
    protected BiomeAnalyzerItemStackHandler() {
        super(BiomeAnalyzerItem.NUM_CUSTOM_SLOTS);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        switch (slot) {
            case BiomeAnalyzerItem.SLOT_CRYSTAL_INPUT:
                return stack.getItem() == ModItems.crystalEmpty.get();

            case BiomeAnalyzerItem.SLOT_ETCHING_INPUT:
                return stack.getItem() == ModItems.etchingFluidBiome.get();

            case BiomeAnalyzerItem.SLOT_OUTPUT:
                return false;

            default:
                throw new DimensionTrekException("Invalid slot requested");
        }
    }

    @Override
    protected boolean isItemValidOutput(int slot, ItemStack stack) {
        return slot == BiomeAnalyzerItem.SLOT_OUTPUT && stack.getItem() == ModItems.crystalBiome.get();
    }
}
