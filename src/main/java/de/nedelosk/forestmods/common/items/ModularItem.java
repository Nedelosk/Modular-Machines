package de.nedelosk.forestmods.common.items;

import de.nedelosk.forestmods.common.core.TabForestMods;
import net.minecraft.item.Item;

public class ModularItem extends Item {

	public ModularItem(String uln) {
		setCreativeTab(TabForestMods.tabForestMods);
		setUnlocalizedName(uln);
	}
}
