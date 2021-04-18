package net.dragonhill.mc.dimensiontrek.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class CachedItemHandlerProvider implements ICapabilitySerializable<INBT> {
    private final LazyOptional<IItemHandler> lazyItemHandlerSupplier = LazyOptional.of(this::getCachedItemHandler);
    private Supplier<IItemHandler> factory;
    private IItemHandler cachedItemHandler;

    public CachedItemHandlerProvider(Supplier<IItemHandler> factory) {
        this.factory = factory;
    }

    private IItemHandler getCachedItemHandler() {
        if(cachedItemHandler == null) {
            cachedItemHandler = factory.get();
            factory = null;
        }

        return cachedItemHandler;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandlerSupplier.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(getCachedItemHandler(), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(getCachedItemHandler(), null, nbt);
    }
}
