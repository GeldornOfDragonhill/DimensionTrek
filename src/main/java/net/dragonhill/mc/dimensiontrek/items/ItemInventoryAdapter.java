package net.dragonhill.mc.dimensiontrek.items;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class ItemInventoryAdapter extends Inventory {

    private static final String NBT_TAG_NAME = "items";
    private static int TAG_LIST = 9;
    private static int TAG_COMPOUND = 10;

    private final ItemStack itemStack;

    public ItemInventoryAdapter(ItemStack itemStack, int inventorySize) {
        super(inventorySize);

        this.itemStack = itemStack;

        readFromNbt();
    }

    @Override
    public void setChanged() {
        super.setChanged();

        writeToNbt();
    }

    private void readFromNbt() {
        CompoundNBT tag = itemStack.getOrCreateTag();

        if(tag.contains(NBT_TAG_NAME, TAG_LIST)) {
            ListNBT list = tag.getList(NBT_TAG_NAME, TAG_COMPOUND);

            for (int i = 0; i < list.size() && i < getContainerSize(); ++i) {
                setItem(i, ItemStack.of(list.getCompound(i)));
            }
        }
    }

    private void writeToNbt() {
        ListNBT list = new ListNBT();
        for (int i = 0; i < getContainerSize(); i++) {
            list.add(getItem(i).save(new CompoundNBT()));
        }

        itemStack.getOrCreateTag().put(NBT_TAG_NAME, list);
    }
}
