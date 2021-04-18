package net.dragonhill.mc.dimensiontrek.items.biomeanalyzer;

import net.dragonhill.mc.dimensiontrek.capabilities.IOutputModifiableItemHandler;
import net.dragonhill.mc.dimensiontrek.config.Config;
import net.dragonhill.mc.dimensiontrek.init.ModContainers;
import net.dragonhill.mc.dimensiontrek.init.ModItems;
import net.dragonhill.mc.dimensiontrek.items.crystal.CrystalItem;
import net.dragonhill.mc.dimensiontrek.ui.InventoryContainerBase;
import net.dragonhill.mc.dimensiontrek.util.observables.IntObservableValue;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class BiomeAnalyzerContainer extends InventoryContainerBase {

    private int numTicksPerCycle;
    private IntObservableValue currentProgress;

    //Server side only values
    String currentBiomeName;

    public int getNumTicksPerCycle() {
        return numTicksPerCycle;
    }

    public int getCurrentProgress() {
        return currentProgress.get();
    }

    //The server constructor
    public BiomeAnalyzerContainer(final int containerId, final PlayerInventory playerInventory, final IOutputModifiableItemHandler outputModifiableItemHandler) {
        super(ModContainers.biomeAnalyzerContainer.get(), containerId, playerInventory, outputModifiableItemHandler);

        BlockPos playerBlockPos = playerInventory.player.blockPosition();
        currentBiomeName = playerInventory.player.level.getBiome(playerBlockPos).getRegistryName().toString();
    }

    //The client constructor
    public BiomeAnalyzerContainer(final int containerId, final PlayerInventory playerInventory, final PacketBuffer extraData) {
        super(ModContainers.biomeAnalyzerContainer.get(), containerId, playerInventory, () -> new BiomeAnalyzerItemStackHandler(), extraData);
    }

    @Override
    public void init() {
        super.init();

        numTicksPerCycle = Config.SERVER.biomeAnalyzerWorkingTicks.get();
    }

    @Override
    protected int getClientHeight() {
        return BiomeAnalyzerScreen.CLIENT_HEIGHT;
    }

    @Override
    protected int getUsedClientWidth() {
        return BiomeAnalyzerScreen.USED_CLIENT_WIDTH;
    }

    @Override
    protected int getUsedClientHeight() {
        return BiomeAnalyzerScreen.USED_CLIENT_HEIGHT;
    }

    @Override
    protected void registerObservableValues() {
        currentProgress = registerObservableValue(new IntObservableValue());
    }

    @Override
    protected void setupCustomInventory() {
        this.addSlotWithEffectiveOffset(getCustomItemHandler(), 0, 27, 1);
        this.addSlotWithEffectiveOffset(getCustomItemHandler(), 1, 1, 24);
        this.addSlotWithEffectiveOffset(getCustomItemHandler(), 2, 27, 49);
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();

        //As this is a server side function, it is used here as some sort of a tick provider
        //The biome analyzer should only progress if the GUI is open, so we trigger the progress logic here
        //Should the biome analyzer be closed before the progress is done, it is reset and nothing is consumed

        ItemStack currentCrystalItemStack = getCustomItemHandler().getStackInSlot(BiomeAnalyzerItem.SLOT_CRYSTAL_INPUT);
        if(currentCrystalItemStack.getCount() <= 0 || currentCrystalItemStack.getItem() != ModItems.crystalEmpty.get()) {
            currentProgress.set(0);
            return;
        }

        ItemStack currentEtchingFluidItemStack = getCustomItemHandler().getStackInSlot(BiomeAnalyzerItem.SLOT_ETCHING_INPUT);
        if(currentEtchingFluidItemStack.getCount() <= 0 || currentEtchingFluidItemStack.getItem() != ModItems.etchingFluidBiome.get()) {
            currentProgress.set(0);
            return;
        }

        if(currentProgress.get() < numTicksPerCycle) {
            currentProgress.set(currentProgress.get() + 1);
            return;
        }

        ItemStack outputItemStack = getCustomItemHandler().getStackInSlot(BiomeAnalyzerItem.SLOT_OUTPUT);

        //Check if the output stack is compatible
        if(!outputItemStack.isEmpty()) {
            if(outputItemStack.getItem() != ModItems.crystalBiome.get()) {
                return;
            }

            CompoundNBT tag = outputItemStack.getTag();

            //Invalid biome crystal if it has no tag
            if(tag == null) {
                return;
            }

            if(tag.getString(CrystalItem.NBT_TAG_BIOME) != currentBiomeName) {
                return;
            }
        }

        CompoundNBT tag = new CompoundNBT();
        tag.putString(CrystalItem.NBT_TAG_BIOME, currentBiomeName);

        ItemStack result = new ItemStack(ModItems.crystalBiome.get(), 1);
        result.setTag(tag);

        if(getCustomItemHandler().insertOutputItem(BiomeAnalyzerItem.SLOT_OUTPUT, result, false).isEmpty()) {
            getCustomItemHandler().extractItem(BiomeAnalyzerItem.SLOT_CRYSTAL_INPUT, 1, false);
            getCustomItemHandler().extractItem(BiomeAnalyzerItem.SLOT_ETCHING_INPUT, 1, false);

            currentProgress.set(0);
        }
    }
}
