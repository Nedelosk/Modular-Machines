package de.nedelosk.forestmods.common.items;

import de.nedelosk.forestmods.library.Tabs;
import de.nedelosk.forestmods.library.core.Registry;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemFlintAxe extends ItemAxe {

	public ItemFlintAxe(ToolMaterial mat) {
		super(mat);
		this.setCreativeTab(Tabs.tabForestMods);
		this.setTextureName("forestmods:tools/axe_flint");
		setUnlocalizedName(Registry.setUnlocalizedItemName("axe.flint"));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Registry.setUnlocalizedItemName("axe.flint");
	}
}
