package net.dragonhill.mc.dimensiontrek.capabilities;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public abstract class OutputModifiableItemStackHandler extends ItemStackHandler implements IOutputModifiableItemHandler {

    protected OutputModifiableItemStackHandler(int size) {
        super(size);
    }

    protected abstract boolean isItemValidOutput(int slot, ItemStack stack);

    //Slightly modified version of insert item for internal output filling usage
    public ItemStack insertOutputItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (!isItemValidOutput(slot, stack))
            return stack;

        validateSlotIndex(slot);

        ItemStack existing = this.stacks.get(slot);

        int limit = getStackLimit(slot, stack);

        if (!existing.isEmpty())
        {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate)
        {
            if (existing.isEmpty())
            {
                this.stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            }
            else
            {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            onContentsChanged(slot);
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount()- limit) : ItemStack.EMPTY;
    }
}
