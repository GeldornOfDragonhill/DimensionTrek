package net.dragonhill.mc.dimensiontrek.capabilities;

import net.dragonhill.mc.dimensiontrek.DimensionTrekException;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public interface IOutputModifiableItemHandler extends IItemHandler {
    ItemStack insertOutputItem(int slot, @Nonnull ItemStack stack, boolean simulate);

    static IOutputModifiableItemHandler getHandler(ICapabilityProvider capabilityProvider) {
        IItemHandler itemHandler = capabilityProvider.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(() -> new DimensionTrekException("No ITEM_HANDLER_CAPABILITY available"));

        if(!(itemHandler instanceof IOutputModifiableItemHandler)) {
            throw new DimensionTrekException("IItemHandler is not an IOutputModifiableItemHandler");
        }

        return (IOutputModifiableItemHandler)itemHandler;
    }
}
