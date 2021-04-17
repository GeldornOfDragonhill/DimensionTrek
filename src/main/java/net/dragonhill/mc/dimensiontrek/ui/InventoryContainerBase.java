package net.dragonhill.mc.dimensiontrek.ui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public abstract class InventoryContainerBase extends Container {

    protected static final int PLAYER_INVENTORY_ROW_SLOTS = 9;
    protected static final int PLAYER_INVENTORY_ROWS = 3;

    protected static final int PLAYER_INVENTORY_STD_X_START = 8;
    protected static final int PLAYER_INVENTORY_SLOT_SIZE = 18;

    protected abstract int getClientHeight();
    abstract protected int getUsedClientWidth();
    abstract protected int getUsedClientHeight();

    protected abstract void setupCustomInventory(IInventory inventory);

    protected InventoryContainerBase(@Nullable ContainerType<?> type, int id, PlayerInventory playerInventory, IInventory customInventory) {
        super(type, id);

        setupStandardInventory(playerInventory);

        setupCustomInventory(customInventory);
    }

    public static void openGui(ServerPlayerEntity player, IServerContainerFactory serverContainerFactory, String guiTitleId) {

        NetworkHooks.openGui(player, new INamedContainerProvider() {

            @Nullable
            @Override
            public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                return serverContainerFactory.create(windowId, playerInventory, playerEntity);
            }

            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent(guiTitleId);
            }
        });
    }

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        //TODO: check if the GUI should still be open?
        return true;
    }

    protected static IInventory getTempClientInventory(int numberOfSlots) {
        return new Inventory(numberOfSlots);
    }

    protected void addSlotWithEffectiveOffset(IInventory inventory, int index, int x, int y) {
        this.addSlot(new Slot(inventory, index, x + (176 - getUsedClientWidth()) / 2, y + InventoryScreen.TOP_PART_HEIGHT + (getClientHeight() - getUsedClientHeight()) / 2));
    }

    private void setupStandardInventory(final PlayerInventory playerInventory) {

        final int yStartHotBar = 59 + InventoryScreen.TOP_PART_HEIGHT + getClientHeight();

        //Add player hot bar
        for (int column = 0; column < PLAYER_INVENTORY_ROW_SLOTS; ++column) {
            this.addSlot(new Slot(playerInventory, column, PLAYER_INVENTORY_STD_X_START + column * PLAYER_INVENTORY_SLOT_SIZE, yStartHotBar));
        }

        final int yStartInventory = 1 + InventoryScreen.TOP_PART_HEIGHT + getClientHeight();

        //Add player inventory
        for (int row = 0; row < PLAYER_INVENTORY_ROWS; ++row) {
            final int currentY = yStartInventory + row * PLAYER_INVENTORY_SLOT_SIZE;
            final int rowInventoryOffset = (row + 1) * PLAYER_INVENTORY_ROW_SLOTS;

            for (int column = 0; column < PLAYER_INVENTORY_ROW_SLOTS; ++column) {
                this.addSlot(new Slot(playerInventory, rowInventoryOffset + column, PLAYER_INVENTORY_STD_X_START + column * PLAYER_INVENTORY_SLOT_SIZE, currentY));
            }
        }
    }
}
