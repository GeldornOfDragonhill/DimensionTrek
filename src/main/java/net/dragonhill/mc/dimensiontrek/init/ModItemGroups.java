package net.dragonhill.mc.dimensiontrek.init;

import net.dragonhill.mc.dimensiontrek.config.Constants;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {
    public static final ItemGroup mainItemGroup = new ItemGroup(Constants.modId) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.biomeAnalyzer.get());
        }
    };
}
