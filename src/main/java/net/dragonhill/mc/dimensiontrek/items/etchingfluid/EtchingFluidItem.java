package net.dragonhill.mc.dimensiontrek.items.etchingfluid;

import net.dragonhill.mc.dimensiontrek.init.ModItemGroups;
import net.dragonhill.mc.dimensiontrek.items.ItemBase;

public class EtchingFluidItem extends ItemBase {
    public EtchingFluidItem() {
        super(properties -> properties
                .stacksTo(64)
                .tab(ModItemGroups.mainItemGroup)
        );
    }
}
