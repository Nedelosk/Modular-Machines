package de.nedelosk.forestmods.common.items;

import de.nedelosk.forestmods.common.core.TabModularMachines;
import net.minecraft.item.Item;

public class ItemModular extends Item {

	public ItemModular(String uln) {
		setCreativeTab(TabModularMachines.tabForestMods);
		setUnlocalizedName(uln);
	}
}
