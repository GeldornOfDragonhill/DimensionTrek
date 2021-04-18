package net.dragonhill.mc.dimensiontrek.items.crystal;

import net.dragonhill.mc.dimensiontrek.init.ModItemGroups;
import net.dragonhill.mc.dimensiontrek.init.ModItems;
import net.dragonhill.mc.dimensiontrek.items.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class CrystalItem extends ItemBase {

    public static final String NBT_TAG_BIOME = "biome";

    public CrystalItem() {
        super(properties -> properties
                .stacksTo(64)
                .tab(ModItemGroups.mainItemGroup)
        );
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.appendHoverText(itemStack, world, tooltip, flag);

        if(itemStack.getItem() != ModItems.crystalBiome.get()) {
            return;
        }

        CompoundNBT tag = itemStack.getTag();

        if(tag == null || !tag.contains(NBT_TAG_BIOME)) {
            tooltip.add(new StringTextComponent("<invalid>").withStyle(TextFormatting.RED));
        }
        else {
            String biomeName = tag.getString(NBT_TAG_BIOME);

            ResourceLocation rl = new ResourceLocation(biomeName);
            String translationKey = "biome." + rl.getNamespace() + "." + rl.getPath();

            tooltip.add(new TranslationTextComponent(translationKey).withStyle(TextFormatting.GREEN));
        }
    }
}
