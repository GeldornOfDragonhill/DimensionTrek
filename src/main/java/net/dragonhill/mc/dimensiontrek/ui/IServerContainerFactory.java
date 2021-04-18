package net.dragonhill.mc.dimensiontrek.ui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;

@FunctionalInterface
public interface IServerContainerFactory<TContainer extends InventoryContainerBase> {
    TContainer create(int containerId, PlayerInventory playerInventory, PlayerEntity playerEntity);
}
