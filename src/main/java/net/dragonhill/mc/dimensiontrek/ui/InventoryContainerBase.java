package net.dragonhill.mc.dimensiontrek.ui;

import net.dragonhill.mc.dimensiontrek.capabilities.IOutputModifiableItemHandler;
import net.dragonhill.mc.dimensiontrek.net.ModNetworkChannel;
import net.dragonhill.mc.dimensiontrek.util.observables.IObservableValueListHolder;
import net.dragonhill.mc.dimensiontrek.util.observables.IntObservableValue;
import net.dragonhill.mc.dimensiontrek.util.observables.ObservableValueList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public abstract class InventoryContainerBase extends Container implements IObservableValueListHolder {

    protected static final int PLAYER_INVENTORY_ROW_SLOTS = 9;
    protected static final int PLAYER_INVENTORY_ROWS = 3;

    protected static final int PLAYER_INVENTORY_SLOTS = PLAYER_INVENTORY_ROW_SLOTS * 4;

    protected static final int PLAYER_INVENTORY_STD_X_START = 8;
    protected static final int PLAYER_INVENTORY_SLOT_SIZE = 18;

    protected abstract int getClientHeight();
    protected abstract int getUsedClientWidth();
    protected abstract int getUsedClientHeight();

    protected abstract void registerObservableValues();
    protected abstract void setupCustomInventory();

    private IOutputModifiableItemHandler customItemHandler;

    //As the base class listeners field is private, have to keep track ourself
    private final Set<ServerPlayerEntity> listeners = new HashSet<>();
    private final ObservableValueList observableValueList = new ObservableValueList();

    protected IOutputModifiableItemHandler getCustomItemHandler() {
        return customItemHandler;
    }

    protected InventoryContainerBase(@Nullable ContainerType<?> type, int containerId, PlayerInventory playerInventory, IOutputModifiableItemHandler customItemHandler) {
        super(type, containerId);

        init(playerInventory, customItemHandler);
    }

    protected InventoryContainerBase(@Nullable ContainerType<?> type, int containerId, PlayerInventory playerInventory, Supplier<IOutputModifiableItemHandler> clientItemHandlerFactory, final PacketBuffer extraData) {
        super(type, containerId);

        init(playerInventory, clientItemHandlerFactory.get());
    }

    private void init(PlayerInventory playerInventory, IOutputModifiableItemHandler customInventory) {
        this.customItemHandler = customInventory;

        setupStandardInventory(playerInventory);
        setupCustomInventory();

        registerObservableValues();

        init();
    }

    public void init() { }

    public static void openGui(ServerPlayerEntity player, IServerContainerFactory serverContainerFactory, String guiTitleId) {

        NetworkHooks.openGui(player, new INamedContainerProvider() {

            @Nullable
            @Override
            public Container createMenu(int containerId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                return serverContainerFactory.create(containerId, playerInventory, playerEntity);
            }

            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent(guiTitleId);
            }
        });
    }

    protected <V extends IntObservableValue> V registerObservableValue(V value) {
        return this.observableValueList.add(value);
    }

    public ObservableValueList getObservableValueList() {
        return observableValueList;
    }

    @Override
    public void addSlotListener(IContainerListener listener) {
        if(listener instanceof ServerPlayerEntity) {
            this.listeners.add((ServerPlayerEntity)listener);
        }

        super.addSlotListener(listener);
    }

    @Override
    public void removeSlotListener(IContainerListener listener) {
        if(listener instanceof ServerPlayerEntity) {
            this.listeners.remove(listener);
        }

        super.removeSlotListener(listener);
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();

        this.observableValueList.createPayloadForChanged(payload -> {

            ScreenClientUpdateMessage message = new ScreenClientUpdateMessage(this.containerId, payload);

            for(ServerPlayerEntity listener : this.listeners) {
                ModNetworkChannel.sendToPlayer(message, listener);
            }
        });
    }

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        //TODO: check if the GUI should still be open?
        return true;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerEntity, int slotIndex) {
        Slot sourceSlot = this.slots.get(slotIndex);

        if(sourceSlot == null || !sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack sourceItemStack = sourceSlot.getItem();

        //Try to move the items from the player inventory to the custom slots or vice versa
        if(slotIndex < PLAYER_INVENTORY_SLOTS) {
            if(!this.moveItemStackTo(sourceItemStack, PLAYER_INVENTORY_SLOTS, PLAYER_INVENTORY_SLOTS + customItemHandler.getSlots(), false)) {
                return ItemStack.EMPTY;
            }
        }
        else {
            if(!this.moveItemStackTo(sourceItemStack, 0, PLAYER_INVENTORY_SLOTS, false)) {
                return ItemStack.EMPTY;
            }
        }

        return sourceItemStack;
    }

    protected void addSlotWithEffectiveOffset(IItemHandler itemHandler, int index, int x, int y) {
        this.addSlot(new SlotItemHandler(itemHandler, index, x + (176 - getUsedClientWidth()) / 2, y + InventoryScreen.TOP_PART_HEIGHT + (getClientHeight() - getUsedClientHeight()) / 2));
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
