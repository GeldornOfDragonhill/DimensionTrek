package net.dragonhill.mc.dimensiontrek.items.biomeanalyzer;

import net.dragonhill.mc.dimensiontrek.capabilities.IOutputModifiableItemHandler;
import net.dragonhill.mc.dimensiontrek.init.ModItemGroups;
import net.dragonhill.mc.dimensiontrek.init.ModItems;
import net.dragonhill.mc.dimensiontrek.capabilities.CachedItemHandlerProvider;
import net.dragonhill.mc.dimensiontrek.items.ItemBase;
import net.dragonhill.mc.dimensiontrek.ui.IServerContainerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class BiomeAnalyzerItem extends ItemBase {

    public static final int NUM_CUSTOM_SLOTS = 3;
    public static final int SLOT_CRYSTAL_INPUT = 0;
    public static final int SLOT_ETCHING_INPUT = 1;
    public static final int SLOT_OUTPUT = 2;

    public BiomeAnalyzerItem() {
        super(properties -> properties
                .stacksTo(1)
                .tab(ModItemGroups.mainItemGroup)
        );
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CachedItemHandlerProvider(() -> new BiomeAnalyzerItemStackHandler());
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getItemInHand(hand);

        if(hand != Hand.MAIN_HAND) {
            return ActionResult.pass(heldItem);
        }

        if(!world.isClientSide) {
            IOutputModifiableItemHandler outputModifiableItemHandler = IOutputModifiableItemHandler.getHandler(heldItem);
            IServerContainerFactory serverContainerFactory = (int containerId, PlayerInventory playerInventory, PlayerEntity playerEntity) -> new BiomeAnalyzerContainer(containerId, playerInventory, outputModifiableItemHandler);
            BiomeAnalyzerContainer.openGui((ServerPlayerEntity) player, serverContainerFactory, ModItems.biomeAnalyzer.get().getDescriptionId());
        }

        return ActionResult.success(heldItem);
    }
}
