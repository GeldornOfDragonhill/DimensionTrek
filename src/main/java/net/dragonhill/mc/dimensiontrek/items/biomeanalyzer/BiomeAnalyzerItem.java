package net.dragonhill.mc.dimensiontrek.items.biomeanalyzer;

import net.dragonhill.mc.dimensiontrek.init.ModItemGroups;
import net.dragonhill.mc.dimensiontrek.init.ModItems;
import net.dragonhill.mc.dimensiontrek.items.ItemBase;
import net.dragonhill.mc.dimensiontrek.items.ItemInventoryAdapter;
import net.dragonhill.mc.dimensiontrek.ui.IServerContainerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BiomeAnalyzerItem extends ItemBase {

    public static final int NUM_CUSTOM_SLOTS = 3;

    public BiomeAnalyzerItem() {
        super(properties -> properties
                .stacksTo(1)
                .tab(ModItemGroups.mainItemGroup)
        );
    }

    public static Inventory getInventory(ItemStack itemStack) {
        return new ItemInventoryAdapter(itemStack, NUM_CUSTOM_SLOTS);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                    return LazyOptional.of(() -> new InvWrapper(getInventory(stack))).cast();
                }

                return LazyOptional.empty();
            }
        };
    }


    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getItemInHand(hand);

        if(hand != Hand.MAIN_HAND) {
            return ActionResult.pass(heldItem);
        }

        if(!world.isClientSide) {
            IServerContainerFactory serverContainerFactory = (int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity)  -> new BiomeAnalyzerContainer(windowId, playerInventory, getInventory(heldItem));
            BiomeAnalyzerContainer.openGui((ServerPlayerEntity) player, serverContainerFactory, ModItems.biomeAnalyzer.get().getDescriptionId());
        }

        return ActionResult.success(heldItem);
    }
}
