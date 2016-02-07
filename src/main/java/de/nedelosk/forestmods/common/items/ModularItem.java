package de.nedelosk.forestmods.common.items;

import de.nedelosk.forestmods.common.core.TabModularMachines;
import net.minecraft.item.Item;

public class ModularItem extends Item {

	public ModularItem(String uln) {
		setCreativeTab(TabModularMachines.tabForestMods);
		setUnlocalizedName(uln);
	}
}
